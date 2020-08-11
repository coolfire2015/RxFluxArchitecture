package com.huyingbao.module.third.ui.action

import com.huyingbao.core.arch.action.ActionCreator
import com.huyingbao.core.arch.action.ActionManager
import com.huyingbao.core.arch.dispatcher.Dispatcher
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.Retrofit
import javax.inject.Inject

@ActivityScoped
class ThirdActionCreator @Inject constructor(
        private val dispatcher: Dispatcher,
        private val actionManager: ActionManager,
        private val retrofit: Retrofit
) : ActionCreator(dispatcher, actionManager), ThirdAction {
}
