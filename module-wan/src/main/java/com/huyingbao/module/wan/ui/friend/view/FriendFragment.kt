package com.huyingbao.module.wan.ui.friend.view

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huyingbao.core.base.fragment.BaseRxFragment
import com.huyingbao.module.wan.R
import com.huyingbao.module.wan.ui.friend.action.FriendActionCreator
import com.huyingbao.module.wan.ui.friend.adapter.WebSiteAdapter
import com.huyingbao.module.wan.ui.friend.model.WebSite
import com.huyingbao.module.wan.ui.friend.store.FriendStore
import java.util.*
import javax.inject.Inject

/**
 * Created by liujunfeng on 2019/1/1.
 */
class FriendFragment : BaseRxFragment<FriendStore>() {
    @Inject
    lateinit var actionCreator: FriendActionCreator

    private var adapter: WebSiteAdapter? = null
    private var rvContent: RecyclerView? = null

    companion object {
        fun newInstance(): FriendFragment {
            return FriendFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.common_fragment_list
    }

    override fun afterCreate(savedInstanceState: Bundle?) {
        setTitle(R.string.wan_label_friend, true)
        initAdapter()
        initRecyclerView()
        showData()
        //如果store已经创建并获取到数据，说明是横屏等操作导致的Fragment重建，不需要重新获取数据
        if (rxStore?.isCreated != null && rxStore?.isCreated!!) {
            return
        }
        refresh()
    }

    /**
     * 实例化adapter
     */
    private fun initAdapter() {
        adapter = WebSiteAdapter(ArrayList())
    }

    /**
     * 实例化RecyclerView
     */
    private fun initRecyclerView() {
        rvContent = view?.findViewById(R.id.rv_content)
        rvContent?.layoutManager = LinearLayoutManager(activity)
        rvContent?.setHasFixedSize(true)
        //view设置适配器
        rvContent?.adapter = adapter
    }

    /**
     * 显示数据
     */
    private fun showData() {
        rxStore?.webSiteListData?.observe(this, Observer { products ->
            if (products != null) {
                setData(products.data)
            }
        })
    }

    /**
     * 刷新
     */
    private fun refresh() {
        actionCreator.getFriendList()
    }

    /**
     * 设置数据
     *
     * @param data
     */
    private fun setData(data: List<WebSite>?) {
        adapter?.setNewData(data)
    }
}
