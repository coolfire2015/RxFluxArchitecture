package com.huyingbao.module.github.ui.login.action

/**
 * Created by liujunfeng on 2019/1/1.
 */
interface LoginAction {

    /**
     * 登录
     */
    fun login(username: String, password: String)

    companion object {
        const val LOGIN = "login"
    }
}
