package com.huyingbao.module.github.api

import com.huyingbao.module.github.ui.login.model.AccessToken
import com.huyingbao.module.github.ui.login.model.LoginRequest
import com.huyingbao.module.github.ui.login.model.User
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

/**
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
    fun authorizations(@Header("Authorization") basicCode: String,
                       @Body authRequest: LoginRequest)
            : Observable<Response<AccessToken>>

    /**
     * 获取用户信息
     */
    @GET("user")
    fun getPersonInfo(@Header("Authorization") basicCode: String)
            : Observable<Response<User>>

}
