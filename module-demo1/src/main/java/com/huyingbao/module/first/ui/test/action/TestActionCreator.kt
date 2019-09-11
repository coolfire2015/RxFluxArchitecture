package com.huyingbao.module.first.ui.test.action

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.first.BuildConfig
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

@ActivityScope
class TestActionCreator @Inject constructor(
        rxDispatcher: RxDispatcher,
        rxActionManager: RxActionManager,
        @param:Named(BuildConfig.MODULE_NAME) private val retrofit: Retrofit
) : RxActionCreator(rxDispatcher, rxActionManager), TestAction {
    override fun getArticle(page: Int) {
        val rxAction = newRxAction(TestAction.GET_ARTICLE, CommonAppConstants.Key.PAGE, page)
        val articleApi = retrofit.create(TestApi::class.java)
        postHttpLoadingAction(rxAction, articleApi.getArticle(page))
    }
}
