package com.huyingbao.core.arch.model

import org.greenrobot.eventbus.EventBusEvent

/**
 * 操作进度通知，发送到[com.huyingbao.core.arch.view.FluxView]
 *
 * @param isLoading true：操作进行中，用于显示操作进度提示，false：操作结束，用于隐藏操作进度提示
 *
 * Created by liujunfeng on 2019/1/1.
 */
class Loading private constructor(
        tag: String,
        val isLoading: Boolean
) : EventBusEvent(tag) {
    companion object {
        fun newInstance(busEvent: EventBusEvent, isLoading: Boolean): Loading {
            return Loading(busEvent.tag, isLoading)
                    .apply { isGlobalCatch = busEvent.isGlobalCatch }
        }
    }
}
