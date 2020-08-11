package com.huyingbao.core.base

import com.huyingbao.core.arch.action.ActionCreator
import com.huyingbao.core.arch.action.ActionManager
import com.huyingbao.core.arch.dispatcher.Dispatcher
import com.huyingbao.core.arch.model.Action
import com.huyingbao.core.arch.model.Change
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 公用的ActionCreator，主要使用其[postLocalChange]等方法。
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
class BaseActionCreator @Inject constructor(
        private val dispatcher: Dispatcher,
        private val rxActionManager: ActionManager
) : ActionCreator(dispatcher, rxActionManager) {
    /**
     * [ActionManager]移除[Action]
     */
    fun removeRxAction(tag: String) {
        removeRxAction(newRxAction(tag))
    }

    /**
     * 不经过[com.huyingbao.core.arch.store.Store]，直接发送[Change]，用于同一线程（主线程）内的变更消息通知。
     */
    fun postLocalChange(tag: String) {
        val rxChange = Change.newInstance(tag)
        postRxChange(rxChange)
    }
}
