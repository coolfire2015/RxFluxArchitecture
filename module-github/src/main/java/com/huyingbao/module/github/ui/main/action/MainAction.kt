package com.huyingbao.module.github.ui.main.action

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