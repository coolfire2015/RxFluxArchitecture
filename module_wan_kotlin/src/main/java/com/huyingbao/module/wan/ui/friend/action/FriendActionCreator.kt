package com.huyingbao.module.wan.ui.friend.action

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.wan.action.WanApi
import javax.inject.Inject

/**
 * @author liujunfeng
 * @date 2019/1/1
 */
@ActivityScope
class FriendActionCreator @Inject
internal constructor(rxDispatcher: RxDispatcher, rxActionManager: RxActionManager) : RxActionCreator(rxDispatcher, rxActionManager), FriendAction {
    @Inject
    lateinit var mWanApi: WanApi

    override fun getFriendList() {
        val action = newRxAction(FriendAction.GET_FRIEND_LIST)
        postHttpRetryAction(action, mWanApi.friendList)
    }
}
