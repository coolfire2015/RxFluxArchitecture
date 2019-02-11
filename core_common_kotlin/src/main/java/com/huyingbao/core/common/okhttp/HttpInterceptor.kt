package com.huyingbao.core.common.okhttp

import android.text.TextUtils

import com.huyingbao.core.common.BuildConfig
import com.orhanobut.logger.Logger

import java.io.IOException

import javax.inject.Inject
import javax.inject.Singleton

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
class HttpInterceptor @Inject
internal constructor() : Interceptor {
    @Volatile
    private var mBaseUrl: String? = null

    fun setBaseUrl(baseUrl: String) {
        mBaseUrl = baseUrl
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()
        val builder = oldRequest.newBuilder()
        builder.addHeader("Content-Type", "application/json;charset=UTF-8")
                .addHeader("Accept", "application/json;charset=UTF-8")
                .addHeader("X-Requested-With", "XMLHttpRequest")
        //设置hostUrl地址
        if (!TextUtils.isEmpty(mBaseUrl)) {
            val newHttpUrl = HttpUrl.parse(mBaseUrl)
            builder.url(oldRequest.url().newBuilder()
                    .scheme(newHttpUrl!!.scheme())
                    .host(newHttpUrl.host())
                    .port(newHttpUrl.port())
                    .build())
        }
        //创建Request
        val request = builder.build()
        //发起请求时间Logger.e(String.format("发送请求 %s", response.request().url()));
        val t1 = System.nanoTime()
        //调用接口,返回数据
        val response = chain.proceed(request)
        // 不打印日志并且数据正常直接返回
        if (!BuildConfig.DEBUG) return response
        val content = response.body()!!.string()
        val t2 = System.nanoTime()
        Logger.e(String.format("接收 for %s in %.1fms", response.request().url(), (t2 - t1) / 1e6))
        Logger.json(content)
        return response.newBuilder().body(ResponseBody.create(response.body()!!.contentType(), content)).build()
    }
}
