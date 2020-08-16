package com.huyingbao.core.progress

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * Created by liujunfeng on 2019/4/10.
 */
open interface DownloadApi {
    /**
     * 下载文件
     *
     * @param tag 下载事件的tag,放在Header中,拦截器拦截获取
     * @param url
     * @return
     */
    @GET
    @Streaming
    suspend fun download(
            @Header(RxProgress.Companion.TAG) tag: String?,
            @Url url: String?): ResponseBody?
}