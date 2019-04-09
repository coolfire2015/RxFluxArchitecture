package com.huyingbao.module.wan.ui.login.action

import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.wan.action.WanActionCreator
import com.huyingbao.module.wan.action.WanApi
import com.huyingbao.module.wan.ui.login.action.LoginAction.Companion.GET_IDENTIFY
import com.huyingbao.module.wan.ui.login.action.LoginAction.Companion.LOGIN
import com.huyingbao.module.wan.ui.login.action.LoginAction.Companion.REGISTER
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by liujunfeng on 2019/1/1.
 */
@ActivityScope
class LoginActionCreator @Inject
internal constructor(rxDispatcher: RxDispatcher, rxActionManager: RxActionManager) : WanActionCreator(rxDispatcher, rxActionManager), LoginAction {
    @Inject
    lateinit var mWanApi: WanApi

    override fun register(username: String, password: String, repassword: String) {
        val rxAction = newRxAction(REGISTER)
        postHttpAction(rxAction, mWanApi.register(username, password, repassword).flatMap(verifyResponse()))
    }

    override fun login(username: String, password: String) {
        val rxAction = newRxAction(LOGIN)
        postHttpAction(rxAction, mWanApi.login(username, password).flatMap(verifyResponse()))
    }

    override fun getIdentify() {
        val rxAction = newRxAction(GET_IDENTIFY)
        val observable = Observable
                .interval(1, TimeUnit.SECONDS)
                .take(10)
                .map({ aLong -> 9 - aLong })
        val coolfire2 = mWanApi.login("coolfire", "123456")
                .flatMap(verifyResponse())
                .flatMap({ userWanResponse -> observable })
        postHttpAction(rxAction, coolfire2)
    }
}
