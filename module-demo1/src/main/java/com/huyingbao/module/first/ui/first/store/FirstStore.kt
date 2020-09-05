package com.huyingbao.module.first.ui.first.store

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.huyingbao.core.arch.dispatcher.Dispatcher
import com.huyingbao.core.arch.model.Action
import com.huyingbao.core.arch.store.ActivityStore
import com.huyingbao.module.first.ui.first.action.FirstAction
import org.greenrobot.eventbus.Subscribe

class FirstStore @ViewModelInject constructor(
        private val dispatcher: Dispatcher
) : ActivityStore(dispatcher) {
    val hotKeyLiveData = MutableLiveData<JsonObject>()

    /**
     * 接收获取目前搜索最多的关键词
     */
    @Subscribe(tags = [FirstAction.GET_HOT_KEY])
    fun onGetHotKey(action: Action) {
        hotKeyLiveData.value = action.getResponse()
    }
}
