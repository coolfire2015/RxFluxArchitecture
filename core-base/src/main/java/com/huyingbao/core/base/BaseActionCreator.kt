package com.huyingbao.core.base

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.arch.model.RxRetry
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

import javax.inject.Inject
import javax.inject.Singleton

/**
 * 公用的ActionCreator，主要使用其[postLocalChange]等方法。
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
class BaseActionCreator @Inject constructor(
        rxDispatcher: RxDispatcher,
        rxActionManager: RxActionManager
) : RxActionCreator(rxDispatcher, rxActionManager) {
    /**
     * [RxActionManager]移除[RxAction]，停止对应的[Disposable]，被观察者[Observable]正在运行的方法会被停止。
     */
    fun removeRxAction(tag: String) {
        removeRxAction(newRxAction(tag))
    }

    /**
     * 不经过[com.huyingbao.core.arch.store.RxStore]，直接发送[RxChange]，用于同一线程（主线程）内的变更消息通知。
     */
    fun postLocalChange(tag: String) {
        val rxChange = RxChange.newInstance(tag)
        postRxChange(rxChange)
    }

    /**
     * 异步执行重试操作，发送[RxAction]
     */
    fun postRetryAction(rxRetry: RxRetry<*>) {
        val rxAction = newRxAction(rxRetry.tag)
        if (hasRxAction(rxAction)) return
        postHttpRetryAction(rxAction, rxRetry.observable)
    }
}
