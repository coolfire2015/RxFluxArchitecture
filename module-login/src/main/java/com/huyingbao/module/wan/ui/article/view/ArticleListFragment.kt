package com.huyingbao.module.wan.ui.article.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.huyingbao.core.arch.model.Error
import com.huyingbao.core.arch.model.Loading
import com.huyingbao.core.arch.view.FluxView
import com.huyingbao.core.base.common.fragment.BaseFragment
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.common.utils.RecyclerItemClickListener
import com.huyingbao.module.common.utils.showCommonError
import com.huyingbao.module.wan.R
import com.huyingbao.module.wan.ui.article.action.ArticleAction
import com.huyingbao.module.wan.ui.article.action.ArticleActionCreator
import com.huyingbao.module.wan.ui.article.adapter.ArticleAdapter
import com.huyingbao.module.wan.ui.article.store.ArticleStore
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

/**
 * Created by liujunfeng on 2019/1/1.
 */
@AndroidEntryPoint
class ArticleListFragment : FluxView, BaseFragment() {
    @Inject
    lateinit var articleActionCreator: ArticleActionCreator

    private val rvContent by lazy {
        view?.findViewById<RecyclerView>(R.id.rv_content)
    }
    private val refreshLayout by lazy {
        view?.findViewById<SmartRefreshLayout>(R.id.rfl_content)
    }
    private val articleAdapter by lazy {
        ArticleAdapter()
    }

    companion object {
        fun newInstance() = ArticleListFragment()
    }

    override val store: ArticleStore by activityViewModels()

    override fun getLayoutId() = R.layout.common_fragment_list

    override fun afterCreate(savedInstanceState: Bundle?) {
//        setTitle(R.string.app_label_wan, false)
        initView()
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        //设置RecyclerView
        rvContent?.apply {
            //RecyclerView设置适配器
            adapter = articleAdapter
            //RecyclerView设置点击事件
            addOnItemTouchListener(
                RecyclerItemClickListener(context, this,
                    object : RecyclerItemClickListener.OnItemClickListener {
                        override fun onItemClick(view: View, position: Int) {
//                            context?.startWebActivity(articleAdapter.getItem(position)?.link,
//                                    articleAdapter.getItem(position)?.title)
                        }
                    })
            )
        }
        //下拉刷新监听器，设置获取最新一页数据
        refreshLayout?.setOnRefreshListener {
            store.pageLiveData.value = ArticleStore.DEFAULT_PAGE
//            getData(null)
        }
        //显示数据
        store.articleLiveData.observe(this@ArticleListFragment, Observer {
            articleAdapter.submitList(it)
        })
    }

    override fun onResume() {
        super.onResume()
        //如果是第一页，需要刷新(排除屏幕旋转)
        if (store.pageLiveData.value == ArticleStore.DEFAULT_PAGE) {
            refreshLayout?.autoRefresh()
        }
    }

    /**
     * 显示进度对话框，接收[Loading]，粘性，该方法不经过RxStore，由FluxView直接处理
     */
    @Subscribe(tags = [ArticleAction.GET_ARTICLE_LIST], sticky = true)
    fun onLoading(rxLoading: Loading) {
        if (!rxLoading.isLoading) {
            refreshLayout?.finishRefresh()
        }
    }

    /**
     * 接收[Error]，粘性
     */
    @Subscribe(tags = [ArticleAction.GET_ARTICLE_LIST], sticky = true)
    fun onError(error: Error) {
        activity?.let { showCommonError(it, error) }
    }

//    /**
//     * 获取数据
//     */
//    @Subscribe(tags = [CommonAppAction.GET_NEXT_PAGE], sticky = true)
//    fun getData(change: Change?) {
//        store?.pageLiveData?.value?.let {
//            articleActionCreator.getArticleList(it)
//        }
//    }
//
//    /**
//     * 滑动到顶部
//     */
//    @Subscribe(tags = [CommonAppAction.SCROLL_TO_TOP], sticky = true)
//    fun scrollToTop(change: Change) {
//        rvContent?.scrollToTop()
//    }

    /**
     * fragment中创建menu
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        //Menu布局文件名同界面布局文件名
        inflater.inflate(R.menu.wan_fragment_article_list, menu)
    }

    /**
     * menu点击事件
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_to_banner -> {
                //跳转BannerFragment
//                baseActionCreator.postLocalChange(ArticleAction.TO_BANNER)
                true
            }
            R.id.menu_to_friend -> {
                //跳转FriendFragment
//                baseActionCreator.postLocalChange(ArticleAction.TO_FRIEND)
                true
            }
            R.id.menu_to_gan -> {
                //跳转module-gan
                ARouter.getInstance().build(CommonAppConstants.Router.RandomActivity).navigation()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
