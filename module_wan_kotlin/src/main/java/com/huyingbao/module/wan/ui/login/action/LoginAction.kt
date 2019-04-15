package com.huyingbao.module.wan.ui.login.action

/**
 * Created by liujunfeng on 2019/1/1.
 */
interface LoginAction {

    /**
     * 注册
     *
     * @param username   用户名
     * @param password   密码
     * @param repassword 密码
     */
    fun register(username: String, password: String, repassword: String)

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     */
    fun login(username: String, password: String)

    /**
     * 发送验证码,10s之后可以重发
     */
    fun getIdentify()

    companion object {
        const val REGISTER = "register"

        const val LOGIN = "login"

        /**
         * 发送验证码,10s之后可以重发
         */
        const val GET_IDENTIFY = "getIdentify"
    }
}
