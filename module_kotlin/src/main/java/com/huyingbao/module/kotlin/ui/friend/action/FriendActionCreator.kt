package com.huyingbao.module.kotlin.ui.friend.action

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.kotlin.action.KotlinApi
import com.huyingbao.module.kotlin.action.KotlinResponse
import com.huyingbao.module.kotlin.ui.friend.model.WebSite

import javax.inject.Inject

/**
 * Created by liujunfeng on 2018/12/26.
 */
@ActivityScope
class FriendActionCreator @Inject
internal constructor(rxDispatcher: RxDispatcher, rxActionManager: RxActionManager) : RxActionCreator(rxDispatcher, rxActionManager), FriendAction {
    @Inject
    internal var mKotlinApi: KotlinApi? = null

    override fun getFriendList() {
        val action = newRxAction(FriendAction.GET_FRIEND_LIST)
        postHttpAction<KotlinResponse<ArrayList<WebSite>>>(action, mKotlinApi!!.friendList)
    }
}
