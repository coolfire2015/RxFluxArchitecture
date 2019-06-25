package com.huyingbao.module.wan.ui.article.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.huyingbao.core.base.fragment.BaseRxFragment
import com.huyingbao.module.wan.R
import com.huyingbao.module.wan.ui.article.action.ArticleActionCreator
import com.huyingbao.module.wan.ui.article.adapter.BannerAdapter
import com.huyingbao.module.wan.ui.article.model.Banner
import com.huyingbao.module.wan.ui.article.store.ArticleStore
import kotlinx.android.synthetic.main.common_fragment_list.*
import java.util.*
import javax.inject.Inject

/**
 * Created by liujunfeng on 2019/1/1.
 */
class BannerFragment : BaseRxFragment<ArticleStore>() {
    @Inject
    lateinit var mActionCreator: ArticleActionCreator

    private var mAdapter: BannerAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.common_fragment_list
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle(R.string.wan_label_banner, true)
        initRecyclerView()
        initAdapter()
        showData()
        //如果store已经创建并获取到数据，说明是横屏等操作导致的Fragment重建，不需要重新获取数据
        if (rxStore!!.bannerLiveData.value != null) {
            return
        }
        refresh()
    }

    /**
     * 实例化RecyclerView
     */
    private fun initRecyclerView() {
        rv_content.layoutManager = LinearLayoutManager(activity)
        rv_content.setHasFixedSize(true)
        //硬件加速
        rv_content.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    /**
     * 实例化adapter
     */
    private fun initAdapter() {
        mAdapter = BannerAdapter(ArrayList())
        //view设置适配器
        rv_content.adapter = mAdapter
    }

    /**
     * 显示数据
     */
    private fun showData() {
        rxStore?.bannerLiveData?.observe(this, Observer { bannerArrayList -> setData(bannerArrayList) })
    }

    /**
     * 刷新
     */
    private fun refresh() {
        mActionCreator.getBannerList()
    }

    /**
     * 设置数据
     *
     * @param data
     */
    private fun setData(data: List<Banner>) {
        mAdapter!!.setNewData(data)
    }

    companion object {

        fun newInstance(): BannerFragment {
            return BannerFragment()
        }
    }
}
