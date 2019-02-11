package com.huyingbao.core.arch.model

/**
 * Created by liujunfeng on 2019/1/1.
 */
class RxError private constructor(tag: String, val throwable: Throwable) : RxEvent(tag) {
    companion object {

        fun newRxError(tag: String, throwable: Throwable): RxError {
            return RxError(tag, throwable)
        }
    }
}
