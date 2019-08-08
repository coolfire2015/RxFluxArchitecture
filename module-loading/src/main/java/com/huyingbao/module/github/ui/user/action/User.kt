package com.huyingbao.module.github.ui.user.action

import com.huyingbao.module.github.ui.login.model.User
import com.huyingbao.module.github.ui.user.model.UserInfoRequest
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH

/**
 * 当前登录用户模块
 *
 * Created by liujunfeng on 2019/6/21.
 */
interface UserAction {
    companion object {
        /**
         * 更新当前用户信息
         */
        const val UPDATE_USER_INFO = "updateUserInfo"
    }

    /**
     * 更新当前用户信息
     */
    fun updateUserInfo(userInfoRequest: UserInfoRequest)
}

interface UserApi {
    /**
     *更新当前用户信息
     */
    @PATCH("user")
    fun updateUserInfo(@Body body: UserInfoRequest): Observable<Response<User>>
}