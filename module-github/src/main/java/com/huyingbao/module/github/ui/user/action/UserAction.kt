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

        const val UPDATE_USER_NAME = "updateUserName"
        const val UPDATE_USER_EMAIL = "updateUserEmail"
        const val UPDATE_USER_COMPANY = "updateUserCompany"
        const val UPDATE_USER_BLOG = "updateUserBlog"
        const val UPDATE_USER_LOCATION = "updateUserLocation"
        const val UPDATE_USER_BIO = "updateUserBio"
    }

}