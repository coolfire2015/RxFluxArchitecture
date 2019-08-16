package com.huyingbao.core.arch.model

import org.greenrobot.eventbus.EventBusEvent

/**
 * 操作异常通知，发送到[com.huyingbao.core.arch.view.RxFluxView]
 *
 * Created by liujunfeng on 2019/1/1.
 */
class RxError private constructor(
        tag: String,
        val throwable: Throwable
) : EventBusEvent(tag) {
    companion object {
        fun newInstance(busEvent: EventBusEvent, throwable: Throwable): RxError {
            return RxError(busEvent.tag, throwable)
                    .apply { isGlobalCatch = busEvent.isGlobalCatch }
        }
    }
}
