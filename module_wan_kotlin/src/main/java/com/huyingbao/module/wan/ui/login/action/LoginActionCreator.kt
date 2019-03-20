package com.huyingbao.module.wan.ui.login.action

import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.wan.action.WanActionCreator
import com.huyingbao.module.wan.action.WanApi
import javax.inject.Inject

/**
 * Created by liujunfeng on 2018/12/26.
 */
@ActivityScope
class LoginActionCreator @Inject
internal constructor(rxDispatcher: RxDispatcher, rxActionManager: RxActionManager) : WanActionCreator(rxDispatcher, rxActionManager), LoginAction {
    @Inject
    lateinit var mWanApi: WanApi

    override fun register(username: String, password: String, repassword: String) {

    }

    override fun login(username: String, password: String) {
        val action = newRxAction(LoginAction.LOGIN)
        postHttpAction(action, mWanApi!!.login(username, password))
    }
}
