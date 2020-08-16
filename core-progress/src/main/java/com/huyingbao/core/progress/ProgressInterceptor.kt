package com.huyingbao.core.progress

import android.text.TextUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 转换ResponseBody为ProgressResponseBody
 * Created by liujunfeng on 2019/4/10.
 */
@Singleton
class ProgressInterceptor @Inject constructor() : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (response.body == null) {
            return response
        }
        //获取Header传递过来的参数,并传递到ProgressResponseBody对象中
        var tag: String? = null
        if (!TextUtils.isEmpty(response.request.header(RxProgress.Companion.TAG))) {
            tag = response.request.header(RxProgress.Companion.TAG)
        }
        val body = ProgressResponseBody(response.body, tag)
        return response.newBuilder().body(body).build()
    }
}