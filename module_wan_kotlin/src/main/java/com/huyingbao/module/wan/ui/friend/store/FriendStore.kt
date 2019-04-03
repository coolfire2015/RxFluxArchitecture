package com.huyingbao.module.wan.ui.friend.store

import androidx.lifecycle.MutableLiveData
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.store.RxFragmentStore
import com.huyingbao.module.wan.action.WanResponse
import com.huyingbao.module.wan.ui.friend.action.FriendAction
import com.huyingbao.module.wan.ui.friend.model.WebSite
import org.greenrobot.eventbus.Subscribe
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author liujunfeng
 * @date 2019/1/1
 */
@Singleton
class FriendStore @Inject
internal constructor(rxDispatcher: RxDispatcher) : RxFragmentStore(rxDispatcher) {
    val webSiteListData = MutableLiveData<WanResponse<ArrayList<WebSite>>>()
    var isCreated: Boolean = false
        private set

    override fun onCleared() {
        super.onCleared()
        isCreated = false
        webSiteListData.value = null
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    override fun onRxAction(rxAction: RxAction) {
        when (rxAction.tag) {
            FriendAction.GET_FRIEND_LIST -> {
                isCreated = true
                webSiteListData.setValue(rxAction.getResponse())
            }
        }
    }
}
