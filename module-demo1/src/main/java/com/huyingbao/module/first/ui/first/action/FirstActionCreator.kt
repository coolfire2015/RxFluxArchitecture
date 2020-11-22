package com.huyingbao.module.first.ui.first.action

import com.huyingbao.core.arch.action.ActionCreator
import com.huyingbao.core.arch.action.ActionManager
import com.huyingbao.core.arch.dispatcher.Dispatcher
import com.huyingbao.module.first.BuildConfig
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

@ActivityRetainedScoped
class FirstActionCreator @Inject constructor(
        dispatcher: Dispatcher,
        actionManager: ActionManager,
        @param:Named(BuildConfig.MODULE_NAME) private val retrofit: Retrofit
) : ActionCreator(dispatcher, actionManager), FirstAction {
    override fun getHotKey() {
        val action = newAction(FirstAction.GET_HOT_KEY)
        postHttpLoadingAction(action, flow {
            emit(retrofit.create(FirstApi::class.java).getHotKey())
        })
    }
}
