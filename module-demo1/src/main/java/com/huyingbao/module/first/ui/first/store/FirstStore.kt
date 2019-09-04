package com.huyingbao.module.first.ui.first.store

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.module.first.ui.first.action.FirstAction
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirstStore @Inject constructor(
        rxDispatcher: RxDispatcher
) : RxActivityStore(rxDispatcher) {
    val hotKeyLiveData = MutableLiveData<JsonObject>()
    /**
     * 接收获取目前搜索最多的关键词
     */
    @Subscribe(tags = [FirstAction.GET_HOT_KEY])
    fun onGetHotKey(rxAction: RxAction) {
        hotKeyLiveData.value = rxAction.getResponse()
    }
}
