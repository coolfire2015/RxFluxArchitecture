package com.huyingbao.module.common.ui.update.action

import com.huyingbao.module.common.ui.update.model.AppBean
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * 通用Action
 *
 * Created by liujunfeng on 2019/6/10.
 */
internal interface DownloadAction {
    /**
     * 开始下载
     *
     * @param tag   下载的唯一标记
     * @param url   下载地址
     * @param local 本地存储地址
     */
    fun downloadStart(tag: String, url: String, local: String): Boolean
}

interface AppAction {
    companion object {
        /**
         * 获取App最新版本信息
         */
        const val GET_APP_LATEST = "getAppLatest"
    }

    /**
     * 获取App最新版本信息
     */
    fun getAppLatest(id: String, token: String)
}

/**
 * App分发平台Fir接口Api
 */
interface FirApi {
    /**
     * 获取App最新版本信息
     */
    @GET("apps/latest/{id}")
    suspend fun getAppLatest(
            @Path("id") id: String,
            @Query("api_token") token: String
    ): Response<AppBean>
}