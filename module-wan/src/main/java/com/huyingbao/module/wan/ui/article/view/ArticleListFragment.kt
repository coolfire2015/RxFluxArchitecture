package com.huyingbao.module.wan.ui.article.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.huyingbao.core.base.fragment.BaseRxFragment
import com.huyingbao.module.wan.R
import com.huyingbao.module.wan.ui.article.action.ArticleAction
import com.huyingbao.module.wan.ui.article.action.ArticleActionCreator
import com.huyingbao.module.wan.ui.article.adapter.ArticleAdapter
import com.huyingbao.module.wan.ui.article.model.Article
import com.huyingbao.module.wan.ui.article.store.ArticleStore
import kotlinx.android.synthetic.main.common_fragment_list.*
import javax.inject.Inject

/**
 * Created by liujunfeng on 2019/1/1.
 */
class ArticleListFragment : BaseRxFragment<ArticleStore>() {
    @Inject
    lateinit var actionCreator: ArticleActionCreator

    private var adapter: ArticleAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.common_fragment_list
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle(R.string.wan_label_article, true)
        initRecyclerView()
        initAdapter()
        showData()
        //如果store已经创建并获取到数据，说明是横屏等操作导致的Fragment重建，不需要重新获取数据
        if (rxStore?.articleLiveData?.value != null) {
            return
        }
        refresh()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        //菜单布局文件名同界面布局文件名
        inflater.inflate(R.menu.wan_fragment_article_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when {
            item.itemId == R.id.menu_to_banner -> {
                actionCreator.postLocalChange(ArticleAction.TO_BANNER)
                true
            }
            item.itemId == R.id.menu_to_friend -> {
                actionCreator.postLocalChange(ArticleAction.TO_FRIEND)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
        adapter = ArticleAdapter(null)
        //设置更多view
        //adapter.setLoadMoreView(new CommonLoadMoreView());
        //设置加载更多监听器
        adapter?.setOnLoadMoreListener({ loadMore() }, rv_content)
        //view设置适配器
        rv_content.adapter = adapter
    }

    /**
     * 显示数据
     */
    private fun showData() {
        rxStore?.articleLiveData?.observe(this, Observer { articleArrayList ->
            //判断获取回来的数据是否是刷新的数据
            val isRefresh = rxStore?.nextRequestPage == 1
            setData(isRefresh, articleArrayList)
            adapter?.setEnableLoadMore(true)
        })
    }

    /**
     * 刷新
     */
    private fun refresh() {
        rxStore?.nextRequestPage = 1
        //这里的作用是防止下拉刷新的时候还可以上拉加载
        adapter?.setEnableLoadMore(false)
        rxStore?.nextRequestPage?.let { actionCreator.getArticleList(it) }
    }

    /**
     * 加载更多
     */
    private fun loadMore() {
        rxStore?.nextRequestPage?.let { actionCreator.getArticleList(it) }
    }

    /**
     * 设置数据
     *
     * @param isRefresh
     * @param data
     */
    private fun setData(isRefresh: Boolean, data: List<Article>?) {
        val size = if (data == null || data.size == 0) 0 else data.size % PAGE_SIZE
        adapter?.setNewData(data)
        if (size == 0) {
            adapter?.loadMoreComplete()
        } else {//最后一页取回的数据不到PAGE_SIZE，说明没有更多数据，结束加载更多操作
            //第一页如果不够一页就不显示没有更多数据布局
            adapter?.loadMoreEnd(isRefresh)
            Toast.makeText(context, "no more data", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private val PAGE_SIZE = 20

        fun newInstance(): ArticleListFragment {
            return ArticleListFragment()
        }
    }
}
