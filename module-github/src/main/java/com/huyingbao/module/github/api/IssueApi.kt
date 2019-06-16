package com.huyingbao.module.github.api

import com.huyingbao.module.github.ui.issue.model.Issue
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface IssueApi {
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
}
