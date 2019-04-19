package com.huyingbao.module.wan.kotlin.action

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.common.model.CommonException
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit

/**
 * 模块自定义的ActionCreator
 * 对所有的接口添加返回结果检查
 * Created by liujunfeng on 2019/1/1.
 */
abstract class WanActionCreator(rxDispatcher: RxDispatcher, rxActionManager: RxActionManager) : RxActionCreator(rxDispatcher, rxActionManager) {

    override fun <T> postHttpAction(rxAction: RxAction, httpObservable: Observable<T>) {
        super.postHttpAction(rxAction, httpObservable.flatMap(verifyResponse()))
    }

    /**
     * 验证接口返回数据是正常
     * 1:没有数据,返回未知异常
     * 2:有数据,返回code不是成功码,返回自定义异常
     */
    fun <T> verifyResponse(): Function<T, Observable<T>> {
        return Function { response ->
            if (response !is WanResponse<*>)
                return@Function Observable.error<T>(CommonException(600, "未知异常！"))
            val errorCode = (response as WanResponse<*>).errorCode
            if (errorCode != 0) {
                val errorMsg = (response as WanResponse<*>).errorMsg
                val exception = CommonException(errorCode, errorMsg)
                return@Function Observable.error<T>(exception)
            }
            Observable.just<T>(response)
        }
    }

    /**
     * 操作重试
     *
     * @param retryNub       失败重试次数
     * @param retryDelayTime 每次重新调用间隔
     * @return
     */
    fun retryAction(retryNub: Int, retryDelayTime: Long): Function<Observable<out Throwable>, Observable<*>> {
        val value = object : BiFunction<Throwable, Int, Any> {
            override fun apply(t1: Throwable, t2: Int): Any {
                return t2
            }
        }
        return Function { observable ->
            observable
                    .zipWith(Observable.range(1, retryNub), value)
                    .flatMap { Observable.timer(retryDelayTime, TimeUnit.SECONDS) }
        }
    }
}
