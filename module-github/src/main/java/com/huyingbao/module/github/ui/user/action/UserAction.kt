package com.huyingbao.module.github.ui.user.action

import com.huyingbao.module.github.ui.user.model.UserInfoRequest

/**
 * 当前登录用户模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
interface UserAction {
    /**
     * 更新当前用户信息
     */
    fun updateUserInfo(userInfoRequest: UserInfoRequest)

    companion object {
        /**
         * 更新当前用户信息
         */
        const val UPDATE_USER_INFO = "updateUserInfo"

        /**
         * 弹框更新当前用户信息
         */
        const val UPDATE_CONTENT = "updateContent"
    }

}