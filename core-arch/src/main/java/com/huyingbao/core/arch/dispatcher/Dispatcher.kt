package com.huyingbao.core.arch.dispatcher

import androidx.lifecycle.ViewModel
import com.huyingbao.core.arch.model.Action
import com.huyingbao.core.arch.model.Change
import com.huyingbao.core.arch.model.Error
import com.huyingbao.core.arch.model.Loading
import com.huyingbao.core.arch.view.SubscriberView
import org.greenrobot.eventbus.EventBus

/**
 * 调度核心类：
 *
 * 1.管理[SubscriberView]、[ViewModel]订阅。
 *
 * 2.发送[Action]、[Change]、[Loading]、[Error]。
 *
 * Created by liujunfeng on 2019/1/1.
 */
object Dispatcher {
    /**
     * [ViewModel]注册订阅。
     */
    fun <T : ViewModel?> subscribeStore(store: T) {
        EventBus.getDefault().register(store)
    }

    /**
     * [SubscriberView]注册订阅。
     */
    fun <T : SubscriberView?> subscribeView(subscriberView: T) {
        EventBus.getDefault().register(subscriberView)
    }

    /**
     * [ViewModel]取消订阅。
     */
    fun <T : ViewModel?> unsubscribeStore(store: T) {
        EventBus.getDefault().unregister(store)
    }

    /**
     * [SubscriberView]取消订阅。
     */
    fun <T : SubscriberView?> unsubscribeView(subscriberView: T) {
        EventBus.getDefault().unregister(subscriberView)
    }

    /**
     * 判断是否注册订阅。
     */
    fun <T> isSubscribe(t: T): Boolean {
        return EventBus.getDefault().isRegistered(t)
    }

    /**
     * 取消所有订阅。
     */
    @Synchronized
    fun unsubscribeAll() {
        EventBus.clearCaches()
        EventBus.getDefault().removeAllStickyEvents()
    }

    /**
     * 发送[Action]到所有订阅的[ViewModel]。
     */
    fun postAction(action: Action) {
        EventBus.getDefault().post(action, action.tag)
    }

    /**
     * 发送[Change]到所有订阅的[SubscriberView]，粘性通知。
     */
    fun postChange(change: Change) {
        EventBus.getDefault().postSticky(change, change.tag)
    }

    /**
     * 发送[Error]到所有订阅的[ViewModel],[SubscriberView]，粘性通知。
     *
     * 发送：操作完成，异常执行状态。
     */
    fun postError(error: Error) {
        EventBus.getDefault().postSticky(error)
    }

    /**
     * 发送[Loading]到所有订阅的[ViewModel],[SubscriberView]，粘性通知。
     *
     * 发送：操作进度。
     */
    fun postLoading(loading: Loading) {
        EventBus.getDefault().postSticky(loading)
    }
}
