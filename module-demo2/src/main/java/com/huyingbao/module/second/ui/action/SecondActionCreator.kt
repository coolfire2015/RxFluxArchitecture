package com.huyingbao.module.second.ui.action

import com.huyingbao.core.arch.action.FlowActionCreator
import com.huyingbao.core.arch.action.FlowActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.module.second.BuildConfig
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

@ActivityScoped
class SecondActionCreator @Inject constructor(
        private val rxDispatcher: RxDispatcher,
        rxActionManager: FlowActionManager,
        @param:Named(BuildConfig.MODULE_NAME) private val retrofit: Retrofit
) : FlowActionCreator(rxDispatcher, rxActionManager), SecondAction {
    override fun getCategories() {
        val rxAction = newRxAction(SecondAction.GET_CATEGORIES)
        postHttpLoadingAction(rxAction, flow { emit(retrofit.create(SecondApi::class.java).getCategories()) })
    }
}
