package com.huyingbao.module.wan.ui.friend.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.huyingbao.core.arch.model.Loading
import com.huyingbao.core.arch.view.FluxView
import com.huyingbao.core.base.common.fragment.BaseFragment
import com.huyingbao.module.common.utils.RecyclerItemClickListener
import com.huyingbao.module.wan.R
import com.huyingbao.module.wan.ui.friend.action.FriendAction
import com.huyingbao.module.wan.ui.friend.action.FriendActionCreator
import com.huyingbao.module.wan.ui.friend.adapter.WebSiteAdapter
import com.huyingbao.module.wan.ui.friend.store.FriendStore
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.Subscribe
import java.util.*
import javax.inject.Inject

/**
 * Created by liujunfeng on 2019/1/1.
 */
@AndroidEntryPoint
class FriendFragment : FluxView, BaseFragment() {
    @Inject
    lateinit var friendActionCreator: FriendActionCreator

    private val rvContent by lazy {
        view?.findViewById<RecyclerView>(R.id.rv_content)
    }
    private val refreshLayout by lazy {
        view?.findViewById<SmartRefreshLayout>(R.id.rfl_content)
    }

    private val webSiteAdapter by lazy {
        WebSiteAdapter(ArrayList())
    }

    companion object {
        fun newInstance() = FriendFragment()
    }

    override val store: FriendStore by viewModels()

    override fun getLayoutId() = R.layout.common_fragment_list

    override fun afterCreate(savedInstanceState: Bundle?) {
//        setTitle(R.string.wan_label_friend, true)
        initView()
    }

    /**
     * 初始化界面
     */
    private fun initView() {
        //设置RecyclerView
        rvContent?.apply {
            //RecyclerView设置适配器
            adapter = webSiteAdapter
            //RecyclerView设置点击事件
            addOnItemTouchListener(
                RecyclerItemClickListener(context, this,
                    object : RecyclerItemClickListener.OnItemClickListener {
                        override fun onItemClick(view: View, position: Int) {
//                            context?.startWebActivity(webSiteAdapter.getItem(position).link, webSiteAdapter.getItem(position).name)
                        }
                    })
            )
        }
        //下拉刷新监听器，设置获取最新一页数据
        refreshLayout?.setOnRefreshListener {
            friendActionCreator.getFriendList()
        }
        //显示数据
        store.webSiteListData.observe(this, Observer { products ->
            if (products != null) {
                webSiteAdapter.setNewInstance(products.data)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        //如果store已经创建并获取到数据，说明是横屏等操作导致的Fragment重建，不需要重新获取数据
        if (store.webSiteListData.value == null) {
            refreshLayout?.autoRefresh()
        }
    }

    /**
     * 显示进度对话框，接收[Loading]，粘性，该方法不经过RxStore，由FluxView直接处理
     */
    @Subscribe(tags = [FriendAction.GET_FRIEND_LIST], sticky = true)
    fun onLoading(rxLoading: Loading) {
        if (!rxLoading.isLoading) {
            refreshLayout?.finishRefresh()
        }
    }

//    /**
//     * 接收[RxRetry]，粘性
//     */
//    @Subscribe(tags = [FriendAction.GET_FRIEND_LIST], sticky = true)
//    fun onRxRetry(rxRetry: Retry<*>) {
//        activity?.let { showCommonRetry(it, rxRetry) }
//    }
//
//    /**
//     * 接收[Error]，粘性
//     */
//    @Subscribe(tags = [FriendAction.GET_FRIEND_LIST], sticky = true)
//    fun onError(error: Error) {
//        activity?.let { showCommonError(it, error) }
//    }
//
//    /**
//     * 滑动到顶部
//     */
//    @Subscribe(tags = [CommonAppAction.SCROLL_TO_TOP], sticky = true)
//    fun scrollToTop(change: Change) {
//        rvContent?.scrollToTop()
//    }
}
