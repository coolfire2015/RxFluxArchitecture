package com.huyingbao.module.github.ui.main.action

import com.huyingbao.core.common.module.CommonContants
import com.huyingbao.module.github.ui.issue.model.Issue
import com.huyingbao.module.github.ui.main.model.Event
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

/**
 * 主页模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
interface MainAction {
    companion object {
        /**
         * 发送用户反馈
         */
        const val FEED_BACK = "feedback"

        /**
         * 获取最新动态
         */
        const val GET_NEWS_EVENT = "getNewsEvent"

        /**
         * 获取趋势数据
         */
        const val GET_TREND_DATA = "getTrendData"
    }

    /**
     * 发送用户反馈
     */
    fun feedback(editContent: String)

    /**
     * 获取最新动态
     */
    fun getNewsEvent(user: String, page: Int)

    /**
     * 获取趋势数据
     *
     * @param languageType  语言
     * @param since         时间（今天/本周/本月）
     */
    fun getTrendData(languageType: String, since: String)
}

interface MainApi {
    /**
     * 向仓库中提交一个新的问题
     */
    @POST("repos/{owner}/{repo}/issues")
    @Headers("Accept: application/vnd.github.html,application/vnd.github.VERSION.raw")
    fun createIssue(
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Body body: Issue

    ): Observable<Response<Issue>>

    /**
     * 获取最新动态
     */
    @GET("users/{user}/received_events")
    fun getNewsEvent(
            @Path("user") user: String,
            @Query("page") page: Int,
            @Query("per_page") perPage: Int = CommonContants.Config.PAGE_SIZE
    ): Observable<Response<ArrayList<Event>>>

    /**
     * 获取趋势数据
     *
     * @param languageType  语言
     * @param since         时间（今天/本周/本月）
     */
    @GET("https://github.com/trending/{languageType}")
    @Headers("Content-Type: text/plain;charset=utf-8")
    fun getTrendData(
            @Header("forceNetWork") forceNetWork: Boolean,
            @Path("languageType") languageType: String,
            @Query("since") since: String): Observable<Response<String>>
}