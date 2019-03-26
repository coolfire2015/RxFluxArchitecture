package com.huyingbao.module.wan.ui.article.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.core.common.R2
import com.huyingbao.core.common.rxview.CommonRxFragment
import com.huyingbao.core.common.widget.CommonLoadMoreView
import com.huyingbao.module.wan.R
import com.huyingbao.module.wan.ui.article.action.ArticleAction
import com.huyingbao.module.wan.ui.article.action.ArticleActionCreator
import com.huyingbao.module.wan.ui.article.adapter.ArticleAdapter
import com.huyingbao.module.wan.ui.article.model.Article
import com.huyingbao.module.wan.ui.article.store.ArticleStore
import javax.inject.Inject

/**
 * @author liujunfeng
 * @date 2019/1/1
 */
@ActivityScope
class ArticleListFragment @Inject
constructor() : CommonRxFragment<ArticleStore>() {
    @Inject
    lateinit var mActionCreator: ArticleActionCreator
    @BindView(R2.id.rv_content)
    lateinit var mRvContent: RecyclerView

    private var mAdapter: ArticleAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.common_fragment_base_list
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle(R.string.wan_label_article, true)
        initRecyclerView()
        initAdapter()
        showData()
        //如果store已经创建并获取到数据，说明是横屏等操作导致的Fragment重建，不需要重新获取数据
        if (rxStore!!.articleLiveData.value != null) return
        refresh()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        //菜单布局文件名同界面布局文件名
        inflater.inflate(R.menu.wan_fragment_article_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_to_banner) {
            mActionCreator.postLocalAction(ArticleAction.TO_BANNER)
            return true
        } else if (item.itemId == R.id.menu_to_friend) {
            mActionCreator.postLocalAction(ArticleAction.TO_FRIEND)
            return true
        } else if (item.itemId == R.id.menu_to_login) {
            mActionCreator.postLocalAction(ArticleAction.TO_LOGIN)
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    /**
     * 实例化RecyclerView
     */
    private fun initRecyclerView() {
        mRvContent.layoutManager = LinearLayoutManager(activity)
        mRvContent.setHasFixedSize(true)
        mRvContent.setLayerType(View.LAYER_TYPE_SOFTWARE, null)//硬件加速
    }

    /**
     * 实例化adapter
     */
    private fun initAdapter() {
        mAdapter = ArticleAdapter(null)
        //设置更多view
        mAdapter!!.setLoadMoreView(CommonLoadMoreView())
        //设置加载更多监听器
        mAdapter!!.setOnLoadMoreListener({ loadMore() }, mRvContent)
        //view设置适配器
        mRvContent.adapter = mAdapter
    }

    /**
     * 显示数据
     */
    private fun showData() {
        rxStore!!.articleLiveData.observe(this, Observer { articleArrayList ->
            if (articleArrayList != null) {
                //判断获取回来的数据是否是刷新的数据
                val isRefresh = rxStore!!.nextRequestPage == 1
                setData(isRefresh, articleArrayList)
                mAdapter!!.setEnableLoadMore(true)
            }
        })
    }

    /**
     * 刷新
     */
    private fun refresh() {
        rxStore!!.nextRequestPage = 1
        mAdapter!!.setEnableLoadMore(false)//这里的作用是防止下拉刷新的时候还可以上拉加载
        mActionCreator.getArticleList(rxStore!!.nextRequestPage)
    }

    /**
     * 加载更多
     */
    private fun loadMore() {
        mActionCreator.getArticleList(rxStore!!.nextRequestPage)
    }

    /**
     * 设置数据
     *
     * @param isRefresh
     * @param data
     */
    private fun setData(isRefresh: Boolean, data: List<Article>?) {
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
