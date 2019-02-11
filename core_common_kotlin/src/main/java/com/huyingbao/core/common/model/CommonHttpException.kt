package com.huyingbao.core.common.model


import java.io.IOException

/**
 * 自定义HttpException
 * Created by liujunfeng on 2019/1/1.
 */
class CommonHttpException(private val mCode: Int, private val mMessage: String) : IOException("HTTP $mCode $mMessage") {

    /**
     * HTTP status mCode.
     */
    fun code(): Int {
        return mCode
    }

    /**
     * HTTP status mMessage.
     */
    fun message(): String {
        return mMessage
    }
}
