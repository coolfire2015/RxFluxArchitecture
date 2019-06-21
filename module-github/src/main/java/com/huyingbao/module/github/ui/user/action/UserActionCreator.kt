package com.huyingbao.module.github.ui.user.action

import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.github.app.GithubActionCreator
import com.huyingbao.module.github.ui.user.model.UserInfoRequest
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * 当前登录用户模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
@ActivityScope
class UserActionCreator @Inject constructor(
        rxDispatcher: RxDispatcher,
        rxActionManager: RxActionManager,
        private val retrofit: Retrofit
) : GithubActionCreator(rxDispatcher, rxActionManager), UserAction {
    override fun updateUserInfo(userInfoRequest: UserInfoRequest) {
        val rxAction = newRxAction(UserAction.UPDATE_USER_INFO)
        postHttpAction(rxAction, retrofit.create(UserApi::class.java).updateUserInfo(userInfoRequest))
    }
}