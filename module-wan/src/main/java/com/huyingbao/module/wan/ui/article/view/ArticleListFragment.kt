package com.huyingbao.module.wan.ui.article.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.huyingbao.core.base.fragment.BaseRxFragment
import com.huyingbao.core.common.module.CommonContants
import com.huyingbao.core.common.module.CommonContants.Config.PAGE_SIZE
import com.huyingbao.core.common.web.view.CommonWebActivity
import com.huyingbao.module.wan.R
import com.huyingbao.module.wan.ui.article.action.ArticleAction
import com.huyingbao.module.wan.ui.article.action.ArticleActionCreator
import com.huyingbao.module.wan.ui.article.adapter.ArticleAdapter
import com.huyingbao.module.wan.ui.article.model.Article
import com.huyingbao.module.wan.ui.article.store.ArticleStore
import javax.inject.Inject

/**
 * Created by liujunfeng on 2019/1/1.
 */
class ArticleListFragment : BaseRxFragment<ArticleStore>() {
    @Inject
    lateinit var articleActionCreator: ArticleActionCreator

    private var articleAdapter: ArticleAdapter? = null
    private var rvContent: RecyclerView? = null

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
        initRecyclerView()
        showData()
        //如果store已经创建并获取到数据，说明是横屏等操作导致的Fragment重建，不需要重新获取数据
        if (rxStore?.articleLiveData?.value != null) {
            return
        }
        refresh()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        //Menu布局文件名同界面布局文件名
        inflater.inflate(R.menu.wan_fragment_article_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when {
            item.itemId == R.id.menu_to_banner -> {
                articleActionCreator.postLocalChange(ArticleAction.TO_BANNER)
                true
            }
            item.itemId == R.id.menu_to_friend -> {
                articleActionCreator.postLocalChange(ArticleAction.TO_FRIEND)
                true
            }
            item.itemId == R.id.menu_to_gan -> {
                //跳转module-gan
                ARouter.getInstance().build(CommonContants.Address.RandomActivity).navigation()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * 实例化adapter
     */
    private fun initAdapter() {
        articleAdapter = ArticleAdapter(null)
        articleAdapter?.run {
            //设置加载更多监听器
            setOnLoadMoreListener({ loadMore() }, rvContent)
            //设置点击事件
            context?.let {
                setOnItemClickListener { _, _, position ->
                    val intent = CommonWebActivity.newIntent(it,
                            articleAdapter?.data?.get(position)?.link,
                            articleAdapter?.data?.get(position)?.title,
                            articleAdapter?.data?.get(position)?.id?.toString(),
                            R.menu.wan_web)
                    startActivity(intent)
                }
            }
        }
    }

    /**
     * 实例化RecyclerView
     */
    private fun initRecyclerView() {
        rvContent = view?.findViewById(R.id.rv_content)
        rvContent?.run {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            //view设置适配器
            adapter = articleAdapter
        }
    }

    /**
     * 显示数据
     */
    private fun showData() {
        rxStore?.articleLiveData?.observe(this, Observer { articleArrayList ->
            //判断获取回来的数据是否是刷新的数据
            val isRefresh = rxStore?.nextRequestPage == 1
            setData(isRefresh, articleArrayList)
            articleAdapter?.setEnableLoadMore(true)
        })
    }

    /**
     * 刷新
     */
    private fun refresh() {
        rxStore?.nextRequestPage = 1
        //这里的作用是防止下拉刷新的时候还可以上拉加载
        articleAdapter?.setEnableLoadMore(false)
        rxStore?.nextRequestPage?.let { articleActionCreator.getArticleList(it) }
    }

    /**
     * 加载更多
     */
    private fun loadMore() {
        rxStore?.nextRequestPage?.let { articleActionCreator.getArticleList(it) }
    }

    /**
     * 设置数据
     */
    private fun setData(isRefresh: Boolean, data: List<Article>?) {
        val size = if (data == null || data.isEmpty()) 0 else data.size % PAGE_SIZE
        articleAdapter?.setNewData(data)
        if (size == 0) {
            articleAdapter?.loadMoreComplete()
        } else {//最后一页取回的数据不到PAGE_SIZE，说明没有更多数据，结束加载更多操作
            //第一页如果不够一页就不显示没有更多数据布局
            articleAdapter?.loadMoreEnd(isRefresh)
            Toast.makeText(context, "no more data", Toast.LENGTH_SHORT).show()
        }
    }
}
