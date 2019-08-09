package com.huyingbao.module.wan.ui.article.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.huyingbao.core.arch.model.RxLoading
import com.huyingbao.core.base.flux.fragment.BaseFluxFragment
import com.huyingbao.module.common.app.CommonAppStore
import com.huyingbao.module.wan.R
import com.huyingbao.module.wan.ui.article.action.ArticleAction
import com.huyingbao.module.wan.ui.article.action.ArticleActionCreator
import com.huyingbao.module.wan.ui.article.adapter.ArticleAdapter
import com.huyingbao.module.wan.ui.article.store.ArticleStore
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.find
import javax.inject.Inject

/**
 * Created by liujunfeng on 2019/1/1.
 */
class ArticleListFragment : BaseFluxFragment<ArticleStore>() {
    @Inject
    lateinit var articleActionCreator: ArticleActionCreator

    private var articleAdapter: ArticleAdapter? = null
    private var refreshLayout: SmartRefreshLayout? = null

    companion object {
        fun newInstance(): ArticleListFragment {
            return ArticleListFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.common_fragment_list
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle(R.string.wan_label_article, true)
        initAdapter()
        initRefreshView()
    }

    /**
     * 设置Adapter
     */
    private fun initAdapter() {
        //实例化adapter
        articleAdapter = ArticleAdapter()
        //RecyclerView设置适配器
        view?.find<RecyclerView>(R.id.rv_content)?.adapter = articleAdapter
        //显示数据
        rxStore?.articleLiveData?.observe(this, Observer {
            articleAdapter?.submitList(it)
        })
    }

    /**
     * 初始化上下拉刷新View
     */
    private fun initRefreshView() {
        refreshLayout = view?.find(R.id.rfl_content)
        refreshLayout?.setOnRefreshListener {
            articleActionCreator.getArticleList(0)
        }
        //如果是新创建，调用刷新方法，排除屏幕旋转
        if (rxStore?.nextRequestPage == 0) {
            refreshLayout?.autoRefresh()
        }
    }

    /**
     * 显示进度对话框
     * 接收[RxLoading]，粘性
     * 该方法不经过RxStore,
     * 由RxFluxView直接处理
     */
    @Subscribe(sticky = true)
    fun onRxLoading(rxLoading: RxLoading) {
        if (!rxLoading.isLoading) {
            refreshLayout?.finishRefresh()
        }
    }

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
        return when {
            item.itemId == R.id.menu_to_banner -> {
                //跳转BannerFragment
                articleActionCreator.postLocalChange(ArticleAction.TO_BANNER)
                true
            }
            item.itemId == R.id.menu_to_friend -> {
                //跳转FriendFragment
                articleActionCreator.postLocalChange(ArticleAction.TO_FRIEND)
                true
            }
            item.itemId == R.id.menu_to_gan -> {
                //跳转module-gan
                ARouter.getInstance().build(CommonAppStore.RandomActivity).navigation()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
