package com.huyingbao.module.gan.ui.main.store

import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.module.gan.ui.main.action.MainAction

import org.greenrobot.eventbus.Subscribe

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
class MainStore @Inject
internal constructor(rxDispatcher: RxDispatcher) : RxActivityStore(rxDispatcher) {

    /**
     * 接收RxAction
     * 处理RxAction携带的数据
     * 发送RxChange通知RxView
     *
     * @param rxAction
     */
    @Subscribe
    override fun onRxAction(rxAction: RxAction) {
        when (rxAction.tag) {
            MainAction.TO_WAN_MODULE, MainAction.TO_GAN_MODULE -> postChange(RxChange.newRxChange(rxAction.tag))
        }
    }
}
