package com.huyingbao.module.wan.ui.friend.action

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.wan.action.WanApi
import javax.inject.Inject

/**
 * Created by liujunfeng on 2019/1/1.
 */
@ActivityScope
class FriendActionCreator @Inject
internal constructor(rxDispatcher: RxDispatcher, rxActionManager: RxActionManager, private val mWanApi: WanApi) : RxActionCreator(rxDispatcher, rxActionManager), FriendAction {

    override fun getFriendList() {
        val rxAction = newRxAction(FriendAction.GET_FRIEND_LIST)
        postHttpRetryAction(rxAction, mWanApi.friendList)
    }
}
