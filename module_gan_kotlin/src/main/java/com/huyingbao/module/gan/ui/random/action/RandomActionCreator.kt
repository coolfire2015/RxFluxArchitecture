package com.huyingbao.module.gan.ui.random.action

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.gan.action.GanApi
import com.huyingbao.module.gan.action.GanConstants

import javax.inject.Inject


/**
 * rxAction创建发送管理类
 * Created by liujunfeng on 2019/1/1.
 */
@ActivityScope
class RandomActionCreator @Inject
internal constructor(rxDispatcher: RxDispatcher, rxActionManager: RxActionManager) : RxActionCreator(rxDispatcher, rxActionManager), RandomAction {
    @Inject
    internal var mGanApi: GanApi? = null

    override fun getProductList(category: String, count: Int, page: Int) {
        val action = newRxAction(RandomAction.GET_PRODUCT_LIST,
                GanConstants.Key.COUNT, count,
                GanConstants.Key.PAGE, page)
        postHttpAction<GanResponse<Product>>(action, mGanApi!!.getDataList(category, count, page))
    }
}
