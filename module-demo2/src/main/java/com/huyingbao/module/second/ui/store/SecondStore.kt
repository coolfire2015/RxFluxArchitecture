package com.huyingbao.module.second.ui.store

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.huyingbao.core.arch.dispatcher.Dispatcher
import com.huyingbao.core.arch.model.Action
import com.huyingbao.core.arch.store.ActivityStore
import com.huyingbao.module.second.ui.action.SecondAction
import org.greenrobot.eventbus.Subscribe

class SecondStore @ViewModelInject constructor(
        override var dispatcher: Dispatcher
) : ActivityStore(dispatcher) {
    val categoryLiveData = MutableLiveData<JsonObject>()

    /**
     * 接收获取闲读的主分类
     */
    @Subscribe(tags = [SecondAction.GET_CATEGORIES])
    fun onGetHotKey(action: Action) {
        categoryLiveData.value = action.getResponse()
    }
}
