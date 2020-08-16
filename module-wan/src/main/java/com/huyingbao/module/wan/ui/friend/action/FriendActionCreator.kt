package com.huyingbao.module.wan.ui.friend.action

import com.huyingbao.core.arch.action.ActionCreator
import com.huyingbao.core.arch.action.ActionManager
import com.huyingbao.core.arch.dispatcher.Dispatcher
import com.huyingbao.module.wan.BuildConfig
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by liujunfeng on 2019/1/1.
 */
@FragmentScoped
class FriendActionCreator @Inject constructor(
        dispatcher: Dispatcher,
        actionManager: ActionManager,
        @param:Named(BuildConfig.MODULE_NAME) private val retrofit: Retrofit
) : ActionCreator(dispatcher, actionManager), FriendAction {
    override fun getFriendList() {
        val rxAction = newAction(FriendAction.GET_FRIEND_LIST)
        rxAction.isGlobalCatch = false
        postHttpLoadingAction(rxAction, flow { emit(retrofit.create(FriendApi::class.java).getFriendList()) })
    }
}
