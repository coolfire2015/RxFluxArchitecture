package com.huyingbao.module.kotlin.action

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.common.model.CommonHttpException

import io.reactivex.Observable
import io.reactivex.functions.Function

/**
 * 模块自定义的ActionCreator
 * 对所有的接口添加返回结果检查
 * Created by liujunfeng on 2019/1/1.
 */
abstract class KotlinActionCreator(rxDispatcher: RxDispatcher, rxActionManager: RxActionManager) : RxActionCreator(rxDispatcher, rxActionManager) {

    override fun <T> postHttpAction(rxAction: RxAction, httpObservable: Observable<T>) {
        super.postHttpAction(rxAction, httpObservable.flatMap(verifyResponse()))
    }

    /**
     * 验证接口返回数据是正常
     * 1:没有数据,返回未知异常
     * 2:有数据,返回code不是成功码,返回自定义异常
     */
    private fun <T> verifyResponse(): Function<T, Observable<T>> {
        return Function{ response ->
            if (response !is KotlinResponse<*>)
                return@Function Observable.error<T>(CommonHttpException(600, "未知异常！"))
            val errorCode = (response as KotlinResponse<*>).errorCode
            if (errorCode != 0) {
                val errorMsg = (response as KotlinResponse<*>).errorMsg
                val exception = CommonHttpException(errorCode, errorMsg)
                return@Function Observable.error<T>(exception)
            }
            Observable.just<T>(response)
        }
    }
}
