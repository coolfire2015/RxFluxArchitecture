package com.huyingbao.core.common.action

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

import javax.inject.Inject
import javax.inject.Singleton

/**
 * 公用的ActionCreator，主要使用其[RxActionCreator.postLocalChange]等方法。
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
class CommonActionCreator @Inject
constructor(rxDispatcher: RxDispatcher, rxActionManager: RxActionManager) : RxActionCreator(rxDispatcher, rxActionManager) {
    /**
     * [RxActionManager]移除[RxAction]，停止对应的[Disposable]，
     * 被观察者[Observable]正在运行的方法会被停止。
     */
    fun removeRxAction(tag: String) {
        removeRxAction(newRxAction(tag))
    }
}
