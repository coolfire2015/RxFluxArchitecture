package com.huyingbao.module.third.ui.action

import com.huyingbao.core.arch.action.FlowActionCreator
import com.huyingbao.core.arch.action.FlowActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.Retrofit
import javax.inject.Inject

@ActivityScoped
class ThirdActionCreator @Inject constructor(
        private val rxDispatcher: RxDispatcher,
        private val rxActionManager: FlowActionManager,
        private val retrofit: Retrofit
) : FlowActionCreator(rxDispatcher, rxActionManager), ThirdAction {
}
