package com.huyingbao.module.github.api

import com.huyingbao.core.common.module.CommonContants
import com.huyingbao.module.github.ui.login.model.AccessToken
import com.huyingbao.module.github.ui.login.model.LoginRequest
import com.huyingbao.module.github.ui.login.model.User
import com.huyingbao.module.github.ui.main.model.Event
import com.huyingbao.module.github.ui.user.model.UserInfoRequest
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

/**
 * 用户信息
 *
 * Created by liujunfeng on 2019/1/1.
 */
interface UserApi {

    /**
     * 登录认证
     *
     * [basicCode] Auth认证中header数据，Github帐号和密码加密串
     */
    @POST("authorizations")
    @Headers("Accept: application/json")
    fun authorizations(
            @Header("Authorization") basicCode: String,
            @Body authRequest: LoginRequest
    ): Observable<Response<AccessToken>>

    /**
     * 获取当前登录用户信息
     */
    @GET("user")
    fun getLoginUserInfo(): Observable<Response<User>>

    /**
     *更新当前用户信息
     */
    @PATCH("user")
    fun updateUserInfo(@Body body: UserInfoRequest): Observable<Response<User>>

    /**
     * 获取最新动态
     */
    @GET("users/{user}/received_events")
    @Headers("forceNetWork: true")
    fun getNewsEvent(
            @Path("user") user: String,
            @Query("page") page: Int,
            @Query("per_page") perPage: Int = CommonContants.Config.PAGE_SIZE
    ): Observable<Response<ArrayList<Event>>>
}
