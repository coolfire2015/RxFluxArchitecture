package com.huyingbao.module.github.ui.main.action

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.module.github.api.UserApi
import com.huyingbao.module.github.app.GithubActionCreator
import retrofit2.Retrofit
import javax.inject.Inject

class MainActionCreator @Inject constructor(
        rxDispatcher: RxDispatcher,
        rxActionManager: RxActionManager,
        private val retrofit: Retrofit
) : GithubActionCreator(rxDispatcher, rxActionManager), MainAction {

    override fun getLoginUserInfo() {
        val rxAction=newRxAction(MainAction.GET_LOGIN_USER_INFO)
        postHttpAction(rxAction,retrofit.create(UserApi::class.java).getLoginUserInfo())
    }
}