package com.huyingbao.module.common.ui.start.store

import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.module.common.ui.start.action.StartAction
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StartStore @Inject constructor(rxDispatcher: RxDispatcher) : RxActivityStore(rxDispatcher) {
    /**
     * 接收[RxAction]，跳转登录页面
     */
    @Subscribe(tags = [StartAction.TO_LOGIN])
    fun onToLogin(rxAction: RxAction) {
        postChange(RxChange.newInstance(rxAction.tag))
    }
}
