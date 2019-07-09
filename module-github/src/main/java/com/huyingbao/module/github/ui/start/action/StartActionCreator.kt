package com.huyingbao.module.github.ui.start.action

import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.github.app.GithubActionCreator
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * 引导模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
@ActivityScope
class StartActionCreator @Inject constructor(
        rxDispatcher: RxDispatcher,
        rxActionManager: RxActionManager,
        private val retrofit: Retrofit
) : GithubActionCreator(rxDispatcher, rxActionManager), StartAction {
    override fun getLoginUserInfo() {
        val rxAction = newRxAction(StartAction.GET_LOGIN_USER_INFO)
        postHttpAction(rxAction, retrofit.create(StartApi::class.java).getLoginUserInfo())
    }
}