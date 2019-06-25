package com.huyingbao.module.github.ui.login.action

import com.huyingbao.module.github.ui.login.model.AccessToken
import com.huyingbao.module.github.ui.login.model.LoginRequest
import com.huyingbao.module.github.ui.login.model.User
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

/**
 * 登录模块
 *
 * Created by liujunfeng on 2019/6/21.
 */
interface LoginAction {
    companion object {
        /**
         * 登录
         */
        const val LOGIN = "login"
        /**
         * 获取登录用户信息
         */
        const val GET_LOGIN_USER_INFO = "getLoginUserInfo"
    }

    /**
     * 登录
     */
    fun login(username: String, password: String)

    /**
     * 获取登录用户信息
     */
    fun getLoginUserInfo()
}

interface LoginApi {
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
}
