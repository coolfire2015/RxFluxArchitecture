package com.huyingbao.module.first.ui.test.store

import com.google.gson.JsonObject
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.arch.store.RxFragmentStore
import com.huyingbao.module.common.app.CommonAppConstants
import com.huyingbao.module.first.ui.test.action.TestAction
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentStore @Inject constructor(
        rxDispatcher: RxDispatcher
) : RxFragmentStore(rxDispatcher) {
    var jsonObject: JsonObject? = null
    var page: Int = 0

    @Subscribe(tags = [TestAction.GET_ARTICLE])
    fun onGetArticle(rxAction: RxAction) {
        jsonObject = rxAction.getResponse()
        page = rxAction.get<Int>(CommonAppConstants.Key.PAGE) ?: 0
        postChange(RxChange.newInstance(TestAction.GET_ARTICLE))
    }
}

