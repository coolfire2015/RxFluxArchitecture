package com.huyingbao.core.base

import com.huyingbao.core.arch.action.FlowActionCreator
import com.huyingbao.core.arch.action.FlowActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.model.RxChange
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 公用的ActionCreator，主要使用其[postLocalChange]等方法。
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
class BaseActionCreator @Inject constructor(
        private val rxDispatcher: RxDispatcher,
        private val rxActionManager: FlowActionManager
) : FlowActionCreator(rxDispatcher, rxActionManager) {
    /**
     * [FlowActionManager]移除[RxAction]
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
}
