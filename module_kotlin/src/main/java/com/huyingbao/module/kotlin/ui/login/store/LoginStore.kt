package com.huyingbao.module.kotlin.ui.login.store

import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.module.kotlin.action.KotlinResponse
import com.huyingbao.module.kotlin.ui.login.action.LoginAction
import com.huyingbao.module.kotlin.ui.login.model.User

import org.greenrobot.eventbus.Subscribe

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
class LoginStore @Inject
constructor(rxDispatcher: RxDispatcher) : RxActivityStore(rxDispatcher) {
    private var mUser: KotlinResponse<User>? = null

    @Subscribe
    override fun onRxAction(rxAction: RxAction) {
        when (rxAction.tag) {
            LoginAction.LOGIN -> {
                mUser = rxAction.getResponse<KotlinResponse<User>>()
                postChange(RxChange.newRxChange(rxAction.tag))
            }
            LoginAction.REGISTER -> {
            }
        }
    }
}
