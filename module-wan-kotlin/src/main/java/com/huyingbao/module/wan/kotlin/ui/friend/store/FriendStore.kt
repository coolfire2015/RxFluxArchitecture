package com.huyingbao.module.wan.kotlin.ui.friend.store

import androidx.lifecycle.MutableLiveData
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.store.RxFragmentStore
import com.huyingbao.module.wan.kotlin.action.WanResponse
import com.huyingbao.module.wan.kotlin.ui.friend.action.FriendAction
import com.huyingbao.module.wan.kotlin.ui.friend.model.WebSite
import org.greenrobot.eventbus.Subscribe
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
class FriendStore @Inject
internal constructor(rxDispatcher: RxDispatcher) : RxFragmentStore(rxDispatcher) {
    val webSiteListData = MutableLiveData<WanResponse<ArrayList<WebSite>>>()
    var isCreated: Boolean = false
        private set

    override fun onCleared() {
        isCreated = false
        webSiteListData.value = null
    }

    @Subscribe(tags = [FriendAction.GET_FRIEND_LIST])
    fun setWebSiteListData(rxAction: RxAction) {
        isCreated = true
        webSiteListData.value = rxAction.getResponse()
    }
}
