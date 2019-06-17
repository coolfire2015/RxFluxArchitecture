package com.huyingbao.module.github.ui.main.store

import androidx.lifecycle.MutableLiveData
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.module.github.ui.main.action.MainAction
import com.huyingbao.module.github.ui.main.model.Event
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 主页模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
@Singleton
class MainStore @Inject constructor(rxDispatcher: RxDispatcher?) : RxActivityStore(rxDispatcher) {
    val eventListLiveData = MutableLiveData<ArrayList<Event>>()
    var nextRequestPage = 1

    @Subscribe(tags = [MainAction.GET_NEWS_EVENT])
    fun onGetNewsEvent(rxAction: RxAction) {
        eventListLiveData.value = rxAction.getResponse()
    }

}