package com.huyingbao.core.arch.model

import io.reactivex.Observable
import org.greenrobot.eventbus.EventBusEvent

/**
 * 操作异常重试通知，发送到[｝][com.huyingbao.core.arch.view.RxFluxView]
 *
 * Created by liujunfeng on 2019/1/1.
 */
class RxRetry<T> private constructor(
        tag: String,
        val throwable: Throwable,
        val observable: Observable<T>
) : EventBusEvent(tag) {
    companion object {
        fun <T> newInstance(busEvent: EventBusEvent, throwable: Throwable, httpObservable: Observable<T>): RxRetry<*> {
            return RxRetry(busEvent.tag, throwable, httpObservable)
                    .apply { isGlobalCatch = busEvent.isGlobalCatch }
        }
    }
}
