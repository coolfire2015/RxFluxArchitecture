package com.huyingbao.module.github.ui.login.action

/**
 * 登录模块
 *
 * Created by liujunfeng on 2019/1/1.
 */
interface LoginAction {

    /**
     * 登录
     */
    fun login(username: String, password: String)

    /**
     * 获取登录用户信息
     */
    fun getLoginUserInfo()

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
}
