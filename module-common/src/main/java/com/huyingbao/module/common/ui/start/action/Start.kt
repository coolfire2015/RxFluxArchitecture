package com.huyingbao.module.common.ui.start.action

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
    }
}
