package com.huyingbao.module.second.ui.store

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.module.second.ui.action.SecondAction
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecondStore @Inject constructor(
        rxDispatcher: RxDispatcher
) : RxActivityStore(rxDispatcher) {
    val categoryLiveData = MutableLiveData<JsonObject>()

    /**
     * 接收获取闲读的主分类
     */
    @Subscribe(tags = [SecondAction.GET_CATEGORIES])
    fun onGetHotKey(rxAction: RxAction) {
        categoryLiveData.value = rxAction.getResponse()
    }
}
