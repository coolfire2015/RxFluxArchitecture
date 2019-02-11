package com.huyingbao.module.kotlin.ui.login.action

/**
 * Created by liujunfeng on 2018/12/26.
 */
interface LoginAction {

    fun register(username: String, password: String, repassword: String)

    fun login(username: String, password: String)

    companion object {
        val REGISTER = "register"

        val LOGIN = "login"
    }
}
