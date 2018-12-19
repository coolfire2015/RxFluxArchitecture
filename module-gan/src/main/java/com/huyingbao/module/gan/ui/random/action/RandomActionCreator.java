package com.huyingbao.module.gan.ui.random.action;

import com.huyingbao.core.action.RxAction;
import com.huyingbao.core.action.RxActionCreator;
import com.huyingbao.core.action.RxActionManager;
import com.huyingbao.core.dispatcher.RxDispatcher;
import com.huyingbao.module.gan.action.GanApi;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * rxAction创建发送管理类
 * Created by liujunfeng on 2017/12/7.
 */
@Singleton
public class RandomActionCreator extends RxActionCreator implements RandomActions {
    @Inject
    GanApi mGanApi;

    @Inject
    public RandomActionCreator(RxDispatcher rxDispatcher, RxActionManager rxActionManager) {
        super(rxDispatcher, rxActionManager);
    }

    @Override
    public void getProductList(String category,int count) {
        RxAction action = newRxAction(GET_PRODUCT_LIST);
        postHttpAction(action, mGanApi.getProductList(
                category,
                count));
    }
}
