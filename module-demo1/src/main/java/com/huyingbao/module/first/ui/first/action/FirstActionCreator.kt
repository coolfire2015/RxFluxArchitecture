package com.huyingbao.module.first.ui.first.action

import com.huyingbao.core.arch.action.FlowActionCreator
import com.huyingbao.core.arch.action.FlowActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.first.BuildConfig
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

@ActivityScope
class FirstActionCreator @Inject constructor(
        rxDispatcher: RxDispatcher,
        rxActionManager: RxActionManager,
        @param:Named(BuildConfig.MODULE_NAME) private val retrofit: Retrofit
) : RxActionCreator(rxDispatcher, rxActionManager), FirstAction {
    override fun getHotKey() {
        val rxAction = newRxAction(FirstAction.GET_HOT_KEY)
        postHttpLoadingAction(rxAction, flow {
            emit(retrofit.create(FirstApi::class.java).getHotKey())
        })
    }
}
