package com.huyingbao.module.first.ui.test.action

import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface TestAction {
    companion object {
        /**
         * 首页文章列表
         */
        const val GET_ARTICLE = "getArticle"
    }

    /**
     * 首页文章列表
     */
    fun getArticle(page: Int)
}

interface TestApi {
    /**
     * 首页文章列表
     */
    @GET("article/list/{page}/json")
    fun getArticle(@Path("page") page: Int): Observable<JsonObject>
}