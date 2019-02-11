package com.huyingbao.core.arch.model

class RxChange private constructor(tag: String) : RxEvent(tag) {
    companion object {

        fun newRxChange(tag: String): RxChange {
            return RxChange(tag)
        }
    }
}
