package com.huyingbao.module.kotlin.ui.login.action

import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.kotlin.action.KotlinActionCreator
import com.huyingbao.module.kotlin.action.KotlinApi
import com.huyingbao.module.kotlin.action.KotlinResponse
import com.huyingbao.module.kotlin.ui.login.model.User

import javax.inject.Inject

/**
 * Created by liujunfeng on 2018/12/26.
 */
@ActivityScope
class LoginActionCreator @Inject
internal constructor(rxDispatcher: RxDispatcher, rxActionManager: RxActionManager) : KotlinActionCreator(rxDispatcher, rxActionManager), LoginAction {
    @Inject
    internal var mKotlinApi: KotlinApi? = null

    override fun register(username: String, password: String, repassword: String) {

    }

    override fun login(username: String, password: String) {
        val action = newRxAction(LoginAction.LOGIN)
        postHttpAction<KotlinResponse<User>>(action, mKotlinApi!!.login(username, password))
    }
}
