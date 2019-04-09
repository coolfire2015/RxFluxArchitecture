package com.huyingbao.module.wan.ui.login.action

/**
 * Created by liujunfeng on 2019/1/1.
 */
interface LoginAction {

    fun register(username: String, password: String, repassword: String)

    fun login(username: String, password: String)

    companion object {
        val REGISTER = "register"

        val LOGIN = "login"
    }
}
