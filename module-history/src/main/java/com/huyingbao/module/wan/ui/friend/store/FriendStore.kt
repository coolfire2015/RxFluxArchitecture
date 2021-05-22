package com.huyingbao.module.history.ui.friend.store

import androidx.lifecycle.MutableLiveData
import com.huyingbao.core.arch.model.Action
import com.huyingbao.core.arch.store.FluxStore
import com.huyingbao.module.history.model.WanResponse
import com.huyingbao.module.history.model.WebSite
import com.huyingbao.module.history.ui.friend.action.FriendAction
import dagger.hilt.android.lifecycle.HiltViewModel
import org.greenrobot.eventbus.Subscribe
import java.util.*
import javax.inject.Inject

/**
 * Created by liujunfeng on 2019/1/1.
 */
@HiltViewModel
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
