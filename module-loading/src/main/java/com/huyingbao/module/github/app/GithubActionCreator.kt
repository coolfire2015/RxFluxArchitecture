package com.huyingbao.module.github.app

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.common.utils.FlatMapUtils
import io.reactivex.Observable

/**
 * 使用[FlatMapUtils.verifyResponse]处理返回结果的父类ActionCreator
 *
 * Created by liujunfeng on 2019/6/10.
 */
abstract class GithubActionCreator(
        rxDispatcher: RxDispatcher,
        rxActionManager: RxActionManager
) : RxActionCreator(rxDispatcher, rxActionManager) {
    override fun <T> postHttpAction(rxAction: RxAction, httpObservable: Observable<T>) {
        super.postHttpAction(rxAction, httpObservable.flatMap<T>(FlatMapUtils.verifyResponse()))
    }

    override fun <T> postHttpLoadingAction(rxAction: RxAction, httpObservable: Observable<T>) {
        super.postHttpLoadingAction(rxAction, httpObservable.flatMap<T>(FlatMapUtils.verifyResponse()))
    }
}