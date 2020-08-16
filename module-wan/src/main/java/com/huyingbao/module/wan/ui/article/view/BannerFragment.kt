package com.huyingbao.module.wan.ui.article.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.huyingbao.core.arch.model.Change
import com.huyingbao.core.arch.model.Error
import com.huyingbao.core.arch.model.Loading
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.core.base.setTitle
import com.huyingbao.core.utils.RecyclerItemClickListener
import com.huyingbao.module.common.app.CommonAppAction
import com.huyingbao.module.common.utils.scrollToTop
import com.huyingbao.module.common.utils.showCommonError
import com.huyingbao.module.common.utils.startWebActivity
import com.huyingbao.module.wan.R
import com.huyingbao.module.wan.ui.article.action.ArticleAction
import com.huyingbao.module.wan.ui.article.action.ArticleActionCreator
import com.huyingbao.module.wan.ui.article.adapter.BannerAdapter
import com.huyingbao.module.wan.ui.article.store.ArticleStore
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.find
import java.util.*
import javax.inject.Inject

/**
 * Created by liujunfeng on 2019/1/1.
 */
class BannerFragment : BaseFluxFragment<ArticleStore>() {
    @Inject
    lateinit var articleActionCreator: ArticleActionCreator

    private val rvContent by lazy {
        view?.find<RecyclerView>(R.id.rv_content)
    }
    private val refreshLayout by lazy {
        view?.find<SmartRefreshLayout>(R.id.rfl_content)
    }

    private val bannerAdapter by lazy {
        BannerAdapter(ArrayList())
    }

    companion object {
        fun newInstance() = BannerFragment()
    }

    override fun getLayoutId() = R.layout.common_fragment_list

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle(R.string.wan_label_banner, true)
        initView()
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        //设置RecyclerView
        rvContent?.apply {
            //RecyclerView设置适配器
            adapter = bannerAdapter
            //RecyclerView设置点击事件
            addOnItemTouchListener(RecyclerItemClickListener(context, this,
                    object : RecyclerItemClickListener.OnItemClickListener {
                        override fun onItemClick(view: View, position: Int) {
                            context?.startWebActivity(bannerAdapter.getItem(position)?.url,
                                    bannerAdapter.getItem(position)?.title)
                        }
                    }))
        }
        //下拉刷新监听器，设置获取最新一页数据
        refreshLayout?.setOnRefreshListener {
            articleActionCreator.getBannerList()
        }
        //显示数据
        store?.bannerLiveData?.observe(this, Observer {
            bannerAdapter.setNewData(it)
        })
    }

    override fun onResume() {
        super.onResume()
        //如果store已经创建并获取到数据，说明是横屏等操作导致的Fragment重建，不需要重新获取数据
        if (store?.bannerLiveData?.value == null) {
            refreshLayout?.autoRefresh()
        }
    }

    /**
     * 显示进度对话框，接收[Loading]，粘性，该方法不经过RxStore，由RxFluxView直接处理
     */
    @Subscribe(tags = [ArticleAction.GET_BANNER_LIST], sticky = true)
    fun onLoading(rxLoading: Loading) {
        if (!rxLoading.isLoading) {
            refreshLayout?.finishRefresh()
        }
    }

    /**
     * 接收[Error]，粘性
     */
    @Subscribe(tags = [ArticleAction.GET_BANNER_LIST], sticky = true)
    fun onError(error: Error) {
        activity?.let { showCommonError(it, error) }
    }

    /**
     * 滑动到顶部
     */
    @Subscribe(tags = [CommonAppAction.SCROLL_TO_TOP], sticky = true)
    fun scrollToTop(change: Change) {
        rvContent?.scrollToTop()
    }
}
