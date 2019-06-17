package com.huyingbao.module.github.ui.main.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.huyingbao.core.base.fragment.BaseRxFragment
import com.huyingbao.module.github.R
import com.huyingbao.module.github.app.GithubAppStore
import com.huyingbao.module.github.ui.main.action.MainActionCreator
import com.huyingbao.module.github.ui.main.adapter.EventAdapter
import com.huyingbao.module.github.ui.main.store.MainStore
import kotlinx.android.synthetic.main.github_fragment_dynamic.*
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

    companion object {
        fun newInstance(): DynamicFragment {
            return DynamicFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.common_fragment_list
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        githubAppStore.userLiveData.value?.login?.let { mainActionCreator.getNewsEvent(it, 1) }
        initRecyclerView()
        initAdapter()
        showData()
    }

    /**
     * 实例化RecyclerView
     */
    private fun initRecyclerView() {
        rv_content.layoutManager = LinearLayoutManager(activity)
        //当Item的改变不会影响RecyclerView的宽高的时候可以设置setHasFixedSize(true)，
        //并通过Adapter的增删改插方法去刷新RecyclerView，而不是通过notifyDataSetChanged()。
        //（其实可以直接设置为true，当需要改变宽高的时候就用notifyDataSetChanged()去整体刷新一下）
        rv_content.setHasFixedSize(true)
        //硬件加速
        rv_content.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    /**
     * 实例化adapter
     */
    private fun initAdapter() {
        mAdapter = EventAdapter(null)
        rv_content.adapter = mAdapter
    }

    /**
     * 显示数据
     */
    private fun showData() {
        rxStore!!.eventListLiveData.observe(this, Observer { articleArrayList ->
            mAdapter!!.setNewData(articleArrayList)
        })
    }
}