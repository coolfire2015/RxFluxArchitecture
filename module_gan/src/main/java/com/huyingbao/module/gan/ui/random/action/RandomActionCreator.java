package com.huyingbao.module.gan.ui.random.action;

import com.huyingbao.core.arch.action.RxActionCreator;
import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.scope.ActivityScope;
import com.huyingbao.module.gan.action.GanApi;
import com.huyingbao.module.gan.action.GanConstants;

import javax.inject.Inject;


/**
 * rxAction创建发送管理类
 * Created by liujunfeng on 2017/12/7.
 */
@ActivityScope
public class RandomActionCreator extends RxActionCreator implements RandomAction {
    @Inject
    GanApi mGanApi;

    @Inject
    RandomActionCreator(RxDispatcher rxDispatcher, RxActionManager rxActionManager) {
        super(rxDispatcher, rxActionManager);
    }

    @Override
    public void getProductList(String category, int count, int page) {
        RxAction action = newRxAction(GET_PRODUCT_LIST,
                GanConstants.Key.COUNT, count,
                GanConstants.Key.PAGE, page);
        postHttpAction(action, mGanApi.getDataList(category, count, page));
    }
}
