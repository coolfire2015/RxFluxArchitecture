package com.huyingbao.module.wan.ui.friend.action

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.scope.FragmentScope
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by liujunfeng on 2019/1/1.
 */
@FragmentScope
class FriendActionCreator @Inject constructor(
        rxDispatcher: RxDispatcher,
        rxActionManager: RxActionManager,
        private val retrofit: Retrofit
) : RxActionCreator(rxDispatcher, rxActionManager), FriendAction {
    override fun getFriendList() {
        val rxAction = newRxAction(FriendAction.GET_FRIEND_LIST)
        postHttpRetryAction(rxAction, retrofit.create(FriendApi::class.java).getFriendList())
    }
}
