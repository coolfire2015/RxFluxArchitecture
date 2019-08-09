package com.huyingbao.core.common.model


import java.io.IOException

/**
 * 自定义Exception
 *
 * Created by liujunfeng on 2017/12/7.
 */
class CommonException(private val mCode: Int, private val mMessage: String?) : IOException("Error Code $mCode $mMessage") {

    /**
     * Error Code.
     */
    fun code(): Int {
        return mCode
    }

    /**
     * Error Message.
     */
    fun message(): String? {
        return mMessage
    }
}
