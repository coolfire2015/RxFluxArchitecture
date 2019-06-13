package com.huyingbao.module.github.ui.main.action

/**
 * 主页模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
interface MainAction {
    /**
     * 获取用户信息
     */
    fun getLoginUserInfo()

    companion object {
        /**
         * 获取用户信息
         */
        const val GET_LOGIN_USER_INFO = "getLoginUserInfo"
    }
}