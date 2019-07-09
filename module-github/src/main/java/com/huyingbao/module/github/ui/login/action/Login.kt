package com.huyingbao.module.github.ui.login.action

import com.huyingbao.module.github.ui.login.model.AccessToken
import com.huyingbao.module.github.ui.login.model.LoginRequest
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

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
    }

    /**
     * 登录
     */
    fun login(username: String, password: String)
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
}
