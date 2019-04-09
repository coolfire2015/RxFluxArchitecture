package com.huyingbao.module.wan.ui.login.store

import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.module.wan.action.WanResponse
import com.huyingbao.module.wan.ui.login.action.LoginAction
import com.huyingbao.module.wan.ui.login.model.User

import org.greenrobot.eventbus.Subscribe

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
class LoginStore @Inject
internal constructor(rxDispatcher: RxDispatcher) : RxActivityStore(rxDispatcher) {
    private var mUser: WanResponse<User>? = null

    @Subscribe(threadMode = ThreadMode.MAIN)
    override fun onRxAction(rxAction: RxAction) {
        when (rxAction.tag) {
            LoginAction.LOGIN -> {
                mUser = rxAction.getResponse<WanResponse<User>>()
                postChange(RxChange.newInstance(rxAction))
            }
            LoginAction.REGISTER -> {
            }
        }
    }
}
