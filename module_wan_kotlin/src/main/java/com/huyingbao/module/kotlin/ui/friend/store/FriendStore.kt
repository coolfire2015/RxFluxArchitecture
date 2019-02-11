package com.huyingbao.module.kotlin.ui.friend.store

import androidx.lifecycle.MutableLiveData
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.store.RxFragmentStore
import com.huyingbao.module.kotlin.action.KotlinResponse
import com.huyingbao.module.kotlin.ui.friend.action.FriendAction
import com.huyingbao.module.kotlin.ui.friend.model.WebSite
import org.greenrobot.eventbus.Subscribe
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2018/12/27.
 */
@Singleton
class FriendStore @Inject
constructor(rxDispatcher: RxDispatcher) : RxFragmentStore(rxDispatcher) {
    val webSiteListData = MutableLiveData<KotlinResponse<ArrayList<WebSite>>>()
    var isCreated: Boolean = false
        private set

    override fun onCleared() {
        super.onCleared()
        isCreated = false
        webSiteListData.value = null
    }

    @Subscribe
    override fun onRxAction(rxAction: RxAction) {
        when (rxAction.tag) {
            FriendAction.GET_FRIEND_LIST -> {
                isCreated = true
                webSiteListData.setValue(rxAction.getResponse())
            }
        }
    }
}
