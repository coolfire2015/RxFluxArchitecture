package com.huyingbao.core.arch.model

import org.greenrobot.eventbus.BusEvent

/**
 * UI响应通知，发送到[com.huyingbao.core.arch.view.FluxView]
 *
 * Created by liujunfeng on 2019/1/1.
 */
class Change private constructor(
    tag: String
) : BusEvent(tag) {
    companion object {
        /**
         * 生成实例对象
         */
        fun newInstance(tag: String): Change {
            return Change(tag)
        }
    }
}
