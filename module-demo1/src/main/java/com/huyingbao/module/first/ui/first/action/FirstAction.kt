package com.huyingbao.module.first.ui.first.action

import com.google.gson.JsonObject
import retrofit2.http.GET

interface FirstAction {
    companion object {
        /**
         * 获取目前搜索最多的关键词
         */
        const val GET_HOT_KEY = "getHotKey"
    }

    /**
     * 获取目前搜索最多的关键词
     */
    fun getHotKey()
}

interface FirstApi {
    /**
     * 获取目前搜索最多的关键词
     */
    @GET("hotkey/json")
    suspend fun getHotKey(): JsonObject
}