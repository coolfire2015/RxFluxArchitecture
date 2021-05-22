package com.huyingbao.module.history.ui.friend.action

import com.huyingbao.core.arch.action.FluxActionCreator
import com.huyingbao.module.history.BuildConfig
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
    @param:Named(BuildConfig.MODULE_NAME) private val retrofit: Retrofit
) : FluxActionCreator(), FriendAction {
    override fun getFriendList() {
        val rxAction = newAction(FriendAction.GET_FRIEND_LIST)
        rxAction.isGlobalCatch = false
        postHttpLoadingAction(
            rxAction,
            flow { emit(retrofit.create(FriendApi::class.java).getFriendList()) })
    }
}
