package com.huyingbao.module.wan.ui.article.view

import android.os.Bundle
import android.view.View

import com.chad.library.adapter.base.BaseQuickAdapter
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.core.common.R2
import com.huyingbao.core.common.rxview.CommonRxFragment
import com.huyingbao.module.wan.R
import com.huyingbao.module.wan.ui.article.action.ArticleActionCreator
import com.huyingbao.module.wan.ui.article.adapter.BannerAdapter
import com.huyingbao.module.wan.ui.article.model.Banner
import com.huyingbao.module.wan.ui.article.store.ArticleStore

import java.util.ArrayList

import javax.inject.Inject

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView

/**
 * Created by liujunfeng on 2019/1/1.
 */
@ActivityScope
class BannerFragment @Inject
constructor() : CommonRxFragment<ArticleStore>() {
    @Inject
    internal var mActionCreator: ArticleActionCreator? = null

    @BindView(R2.id.rv_content)
    internal var mRvContent: RecyclerView? = null

    private var mDataList: List<Banner>? = null
    private var mAdapter: BaseQuickAdapter<*, *>? = null

    override fun getLayoutId(): Int {
        return R.layout.common_fragment_base_list
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle(R.string.wan_label_banner, true)
        initRecyclerView()
        initAdapter()
        showData()
        //如果store已经创建并获取到数据，说明是横屏等操作导致的Fragment重建，不需要重新获取数据
        if (rxStore!!.bannerLiveData.value != null) return
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
        mDataList = ArrayList()
        mAdapter = BannerAdapter(mDataList)
        //view设置适配器
        mRvContent!!.adapter = mAdapter
    }

    /**
     * 显示数据
     */
    private fun showData() {
        rxStore!!.bannerLiveData.observe(this, { bannerArrayList -> setData(bannerArrayList) })
    }

    /**
     * 刷新
     */
    private fun refresh() {
        mActionCreator!!.getBannerList()
    }

    /**
     * 设置数据
     *
     * @param data
     */
    private fun setData(data: List<Banner>) {
        mAdapter!!.setNewData(data)
    }
}
