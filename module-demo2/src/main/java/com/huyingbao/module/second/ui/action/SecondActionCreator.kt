package com.huyingbao.module.second.ui.action

import com.huyingbao.core.arch.action.ActionCreator
import com.huyingbao.core.arch.action.ActionManager
import com.huyingbao.core.arch.dispatcher.Dispatcher
import com.huyingbao.module.second.BuildConfig
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

@ActivityScoped
class SecondActionCreator @Inject constructor(
        private val dispatcher: Dispatcher,
        actionManager: ActionManager,
        @param:Named(BuildConfig.MODULE_NAME) private val retrofit: Retrofit
) : ActionCreator(dispatcher, actionManager), SecondAction {
    override fun getCategories() {
        val action = newAction(SecondAction.GET_CATEGORIES)
        postHttpLoadingAction(action, flow { emit(retrofit.create(SecondApi::class.java).getCategories()) })
    }
}
