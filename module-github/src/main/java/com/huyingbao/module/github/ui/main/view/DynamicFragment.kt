package com.huyingbao.module.github.ui.main.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huyingbao.core.base.fragment.BaseRxFragment
import com.huyingbao.module.github.R
import com.huyingbao.module.github.app.GithubAppStore
import com.huyingbao.module.github.ui.main.action.MainActionCreator
import com.huyingbao.module.github.ui.main.adapter.EventAdapter
import com.huyingbao.module.github.ui.main.store.MainStore
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import org.jetbrains.anko.find
import javax.inject.Inject

/**
 * 动态页面
 *
 * Created by liujunfeng on 2019/6/10.
 */
class DynamicFragment : BaseRxFragment<MainStore>() {
    @Inject
    lateinit var mainActionCreator: MainActionCreator
    @Inject
    lateinit var githubAppStore: GithubAppStore

    private var mAdapter: EventAdapter? = null
    private var rfl_content: SmartRefreshLayout? = null
    private var rv_content: RecyclerView? = null

    companion object {
        fun newInstance(): DynamicFragment {
            return DynamicFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.common_fragment_list
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        initRefreshView()
        initRecyclerView()
        initAdapter()
        showData()
    }

    /**
     * 初始化上下拉刷新View
     */
    private fun initRefreshView() {
        rfl_content = view?.find(R.id.rfl_content)
        rv_content = view?.find(R.id.rv_content)
        rfl_content?.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mainActionCreator.getNewsEvent(githubAppStore.userLiveData.value?.login ?: "", 1)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                mainActionCreator.getNewsEvent(githubAppStore.userLiveData.value?.login ?: "", 1)
            }
        })
        rfl_content?.autoRefresh()
    }

    /**
     * 初始化RecyclerView
     */
    private fun initRecyclerView() {
        rv_content?.layoutManager = LinearLayoutManager(activity)
        //当Item的改变不会影响RecyclerView的宽高的时候可以设置setHasFixedSize(true)，
        //并通过Adapter的增删改插方法去刷新RecyclerView，而不是通过notifyDataSetChanged()。
        //（其实可以直接设置为true，当需要改变宽高的时候就用notifyDataSetChanged()去整体刷新一下）
        rv_content?.setHasFixedSize(true)
        //硬件加速
        rv_content?.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    /**
     * 初始化adapter
     */
    private fun initAdapter() {
        mAdapter = EventAdapter(null)
        rv_content?.adapter = mAdapter
    }

    /**
     * 显示数据
     */
    private fun showData() {
        rxStore!!.eventListLiveData.observe(this, Observer { articleArrayList ->
            if (articleArrayList != null) {
                mAdapter!!.setNewData(articleArrayList)
                //关闭下拉
                rfl_content?.finishRefresh()
                rfl_content?.finishLoadMore()
            }
        })
    }
}