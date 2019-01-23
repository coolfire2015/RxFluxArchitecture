package com.huyingbao.module.kotlin.ui.friend.view

import android.os.Bundle
import android.view.View

import com.chad.library.adapter.base.BaseQuickAdapter
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.core.common.R2
import com.huyingbao.core.common.view.CommonRxFragment
import com.huyingbao.module.kotlin.R
import com.huyingbao.module.kotlin.ui.friend.action.FriendActionCreator
import com.huyingbao.module.kotlin.ui.friend.adapter.WebSiteAdapter
import com.huyingbao.module.kotlin.ui.friend.model.WebSite
import com.huyingbao.module.kotlin.ui.friend.store.FriendStore

import org.greenrobot.eventbus.Subscribe

import java.util.ArrayList

import javax.inject.Inject
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView

/**
 * Created by liujunfeng on 2019/1/1.
 */
@ActivityScope
class FriendFragment @Inject
constructor() : CommonRxFragment<FriendStore>() {
    @Inject
    internal var mActionCreator: FriendActionCreator? = null

    @BindView(R2.id.rv_content)
    internal var mRvContent: RecyclerView? = null

    private var mDataList: List<WebSite>? = null
    private var mAdapter: BaseQuickAdapter<*, *>? = null

    override fun getLayoutId(): Int {
        return R.layout.common_fragment_base_list
    }

    override fun afterCreate(savedInstanceState: Bundle) {
        setTitle(R.string.kotlin_label_friend, true)
        initRecyclerView()
        initAdapter()
        showData()
        //如果store已经创建并获取到数据，说明是横屏等操作导致的Fragment重建，不需要重新获取数据
        if (rxStore!!.isCreated) return
        refresh()
    }

    @Subscribe(sticky = true)
    override fun onRxChanged(rxChange: RxChange) {
        super.onRxChanged(rxChange)
    }

    /**
     * 实例化RecyclerView
     */
    private fun initRecyclerView() {
        mRvContent!!.layoutManager = LinearLayoutManager(activity)
        mRvContent!!.setHasFixedSize(true)
        mRvContent!!.setLayerType(View.LAYER_TYPE_SOFTWARE, null)//硬件加速
    }

    /**
     * 实例化adapter
     */
    private fun initAdapter() {
        mDataList = ArrayList()
        mAdapter = WebSiteAdapter(mDataList)
        //view设置适配器
        mRvContent!!.adapter = mAdapter
    }

    /**
     * 显示数据
     */
    private fun showData() {
        rxStore!!.webSiteListData.observe(this, { products ->
            if (products == null) return@getRxStore ().getWebSiteListData().observe
            setData(products!!.data)
        })
    }

    /**
     * 刷新
     */
    private fun refresh() {
        mActionCreator!!.getFriendList()
    }

    /**
     * 设置数据
     *
     * @param data
     */
    private fun setData(data: List<WebSite>?) {
        mAdapter!!.setNewData(data)
    }
}
