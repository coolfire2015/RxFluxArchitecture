package com.huyingbao.module.second.ui.action

import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface SecondAction {
    companion object {
        /**
         * 获取闲读的主分类
         */
        const val GET_CATEGORIES = "getCategories"
    }

    /**
     * 获取闲读的主分类
     */
    fun getCategories()
}

interface SecondApi {
    /**
     * 获取闲读的主分类
     */
    @GET("xiandu/categories ")
    fun getCategories(): Observable<JsonObject>
}