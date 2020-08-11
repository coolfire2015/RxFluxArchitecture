package com.huyingbao.module.first.ui.first.action

import com.huyingbao.core.arch.action.ActionCreator
import com.huyingbao.core.arch.action.ActionManager
import com.huyingbao.core.arch.dispatcher.Dispatcher
import com.huyingbao.module.first.BuildConfig
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

@ActivityScoped
class FirstActionCreator @Inject constructor(
        dispatcher: Dispatcher,
        rxActionManager: ActionManager,
        @param:Named(BuildConfig.MODULE_NAME) private val retrofit: Retrofit
) : ActionCreator(dispatcher, rxActionManager), FirstAction {
    override fun getHotKey() {
        val rxAction = newRxAction(FirstAction.GET_HOT_KEY)
        postHttpLoadingAction(rxAction, flow {
            emit(retrofit.create(FirstApi::class.java).getHotKey())
        })
    }
}
