package com.huyingbao.module.github.ui.start.action

import com.huyingbao.module.github.ui.login.model.User
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

/**
 * 登录模块
 *
 * Created by liujunfeng on 2019/6/21.
 */
interface StartAction {
    companion object {
        /**
         * 跳转登录页面
         */
        const val TO_LOGIN = "toLogin"
        /**
         * 跳转主页面
         */
        const val TO_MAIN = "toMain"
        /**
         * 获取当前登录用户信息
         */
        const val GET_LOGIN_USER_INFO = "getLoginUserInfo"
    }

    /**
     * 获取当前登录用户信息
     */
    fun getLoginUserInfo()
}

interface StartApi {
    /**
     * 获取当前登录用户信息
     */
    @GET("user")
    fun getLoginUserInfo(): Observable<Response<User>>
}
