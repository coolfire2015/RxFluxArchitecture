package com.huyingbao.core.arch.model

import org.greenrobot.eventbus.BusEvent

/**
 * 操作异常通知，发送到[com.huyingbao.core.arch.view.FluxView]
 *
 * Created by liujunfeng on 2019/1/1.
 */
class Error private constructor(
        tag: String,
        val throwable: Throwable
) : BusEvent(tag) {
    companion object {
        fun newInstance(busEvent: BusEvent, throwable: Throwable): Error {
            return Error(busEvent.tag, throwable)
                    .apply { isGlobalCatch = busEvent.isGlobalCatch }
        }
    }
}
