package com.huyingbao.module.wan.ui.article.view

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huyingbao.core.base.fragment.BaseRxFragment
import com.huyingbao.module.wan.R
import com.huyingbao.module.wan.ui.article.action.ArticleActionCreator
import com.huyingbao.module.wan.ui.article.adapter.BannerAdapter
import com.huyingbao.module.wan.ui.article.model.Banner
import com.huyingbao.module.wan.ui.article.store.ArticleStore
import java.util.*
import javax.inject.Inject

/**
 * Created by liujunfeng on 2019/1/1.
 */
class BannerFragment : BaseRxFragment<ArticleStore>() {
    @Inject
    lateinit var articleActionCreator: ArticleActionCreator

    private var bannerAdapter: BannerAdapter? = null
    private var rvContent: RecyclerView? = null

    companion object {
        fun newInstance(): BannerFragment {
            return BannerFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.common_fragment_list
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle(R.string.wan_label_banner, true)
        initAdapter()
        initRecyclerView()
        showData()
        //如果store已经创建并获取到数据，说明是横屏等操作导致的Fragment重建，不需要重新获取数据
        if (rxStore!!.bannerLiveData.value != null) {
            return
        }
        refresh()
    }

    /**
     * 实例化adapter
     */
    private fun initAdapter() {
        bannerAdapter = BannerAdapter(ArrayList())
    }

    /**
     * 实例化RecyclerView
     */
    private fun initRecyclerView() {
        rvContent = view?.findViewById(R.id.rv_content)
        rvContent?.layoutManager = LinearLayoutManager(activity)
        rvContent?.setHasFixedSize(true)
        //view设置适配器
        rvContent?.adapter = bannerAdapter
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
        articleActionCreator.getBannerList()
    }

    /**
     * 设置数据
     */
    private fun setData(data: List<Banner>?) {
        bannerAdapter?.setNewData(data)
    }
}
