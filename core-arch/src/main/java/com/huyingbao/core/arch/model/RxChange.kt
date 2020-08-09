package com.huyingbao.core.arch.model

import org.greenrobot.eventbus.EventBusEvent

/**
 * UI响应通知，发送到[com.huyingbao.core.arch.view.RxFluxView]
 *
 * Created by liujunfeng on 2019/1/1.
 */
class RxChange private constructor(
        tag: String
) : EventBusEvent(tag) {
    companion object {
        fun newInstance(tag: String): RxChange {
            return RxChange(tag)
        }
    }
}
