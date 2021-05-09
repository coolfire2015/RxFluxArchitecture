package com.huyingbao.module.wan.ui.friend.store

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.huyingbao.core.arch.model.Action
import com.huyingbao.core.arch.store.FluxStore
import com.huyingbao.module.wan.model.WanResponse
import com.huyingbao.module.wan.model.WebSite
import com.huyingbao.module.wan.ui.friend.action.FriendAction
import org.greenrobot.eventbus.Subscribe
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2019/1/1.
 */
@ViewModelInject
class FriendStore @Inject constructor(
) : FluxStore() {
    val webSiteListData = MutableLiveData<WanResponse<ArrayList<WebSite>>>()

    override fun onCleared() {
        super.onCleared()
        webSiteListData.value = null
    }

    @Subscribe(tags = [FriendAction.GET_FRIEND_LIST])
    fun setWebSiteListData(rxAction: Action) {
        webSiteListData.value = rxAction.getResponse()
    }
}
