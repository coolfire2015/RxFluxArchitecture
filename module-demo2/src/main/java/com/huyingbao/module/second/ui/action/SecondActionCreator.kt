package com.huyingbao.module.second.ui.action

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.second.BuildConfig
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

@ActivityScope
class SecondActionCreator @Inject constructor(
        rxDispatcher: RxDispatcher,
        rxActionManager: RxActionManager,
        @param:Named(BuildConfig.MODULE_NAME) private val retrofit: Retrofit
) : RxActionCreator(rxDispatcher, rxActionManager), SecondAction {
    override fun getCategories() {
        val rxAction = newRxAction(SecondAction.GET_CATEGORIES)
        postHttpLoadingAction(rxAction, retrofit.create(SecondApi::class.java).getCategories())
    }
}
