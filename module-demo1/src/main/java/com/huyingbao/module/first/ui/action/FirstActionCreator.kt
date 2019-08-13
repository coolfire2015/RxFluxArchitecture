package com.huyingbao.module.first.ui.action

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.first.BuildConfig
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
        postHttpLoadingAction(rxAction, retrofit.create(FirstApi::class.java).getHotKey())
    }
}
