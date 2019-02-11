package com.huyingbao.module.gan.ui.random.view

import android.os.Bundle
import android.view.View
import android.widget.Toast

import com.chad.library.adapter.base.BaseQuickAdapter
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.core.common.R2
import com.huyingbao.core.common.view.CommonRxFragment
import com.huyingbao.core.common.widget.CommonLoadMoreView
import com.huyingbao.module.gan.R
import com.huyingbao.module.gan.ui.random.action.RandomActionCreator
import com.huyingbao.module.gan.ui.random.adapter.ProductAdapter
import com.huyingbao.module.gan.ui.random.model.Product
import com.huyingbao.module.gan.ui.random.store.RandomStore

import javax.inject.Inject

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView

/**
 * Created by liujunfeng on 2019/1/1.
 */
@ActivityScope
class ProductFragment @Inject
constructor() : CommonRxFragment<RandomStore>() {
    @Inject
    internal var mActionCreator: RandomActionCreator? = null
    @BindView(R2.id.rv_content)
    internal var mRvContent: RecyclerView? = null

    private var mAdapter: BaseQuickAdapter<*, *>? = null

    override fun getLayoutId(): Int {
        return R.layout.common_fragment_base_list
    }

    override fun afterCreate(savedInstanceState: Bundle) {
        setTitle(getRxStore()!!.category, true)
        initRecyclerView()
        initAdapter()
        showData()
        //如果store已经创建并获取到数据，说明是横屏等操作导致的Fragment重建，不需要重新获取数据
        if (getRxStore()!!.productListLiveData.getValue() != null) return
        refresh()
    }

    /**
     * 实例化RecyclerView
     */
    private fun initRecyclerView() {
        mRvContent!!.layoutManager = LinearLayoutManager(activity)
        mRvContent!!.setHasFixedSize(true)
        mRvContent!!.setLayerType(View.LAYER_TYPE_SOFTWARE, null)//硬件加速
    }

    /**
     * 实例化adapter
     */
    private fun initAdapter() {
        mAdapter = ProductAdapter(null)
        //设置更多view
        mAdapter!!.setLoadMoreView(CommonLoadMoreView())
        //设置加载更多监听器
        mAdapter!!.setOnLoadMoreListener({ loadMore() }, mRvContent)
        //view设置适配器
        mRvContent!!.adapter = mAdapter
    }

    /**
     * 显示数据
     */
    private fun showData() {
        getRxStore()!!.productListLiveData.observe(this, { productList ->
            if (productList == null) return@getRxStore ().getProductListLiveData().observe
            //判断获取回来的数据是否是刷新的数据
            val isRefresh = getRxStore()!!.nextRequestPage == 1
            setData(isRefresh, productList)
            mAdapter!!.setEnableLoadMore(true)
        })
    }

    /**
     * 刷新
     */
    private fun refresh() {
        getRxStore()!!.nextRequestPage = 1
        mAdapter!!.setEnableLoadMore(false)//这里的作用是防止下拉刷新的时候还可以上拉加载
        mActionCreator!!.getProductList(getRxStore()!!.category, PAGE_SIZE, getRxStore()!!.nextRequestPage)
    }

    /**
     * 加载更多
     */
    private fun loadMore() {
        mActionCreator!!.getProductList(getRxStore()!!.category, PAGE_SIZE, getRxStore()!!.nextRequestPage)
    }

    /**
     * 设置数据
     *
     * @param isRefresh
     * @param data
     */
    private fun setData(isRefresh: Boolean, data: List<Product>?) {
        val size = if (data == null || data.size == 0) 0 else data.size % PAGE_SIZE
        mAdapter!!.setNewData(data)
        if (size == 0) {
            mAdapter!!.loadMoreComplete()
        } else {//最后一页取回的数据不到PAGE_SIZE，说明没有更多数据，结束加载更多操作
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter!!.loadMoreEnd(isRefresh)
            Toast.makeText(context, "no more data", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private val PAGE_SIZE = 20
    }
}
