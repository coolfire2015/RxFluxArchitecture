package com.huyingbao.module.gan.ui.day.action;

import com.huyingbao.core.action.RxAction;
import com.huyingbao.core.action.RxActionCreator;
import com.huyingbao.core.action.RxActionManager;
import com.huyingbao.core.dispatcher.Dispatcher;
import com.huyingbao.module.gan.action.GanApi;
import com.huyingbao.module.gan.ui.random.action.RandomActions;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * rxAction创建发送管理类
 * Created by liujunfeng on 2017/12/7.
 */
@Singleton
public class DayActionCreator extends RxActionCreator implements RandomActions {
    @Inject
    GanApi mGanApi;

    @Inject
    public DayActionCreator(Dispatcher dispatcher, RxActionManager rxActionManager) {
        super(dispatcher, rxActionManager);
    }

    @Override
    public void getProductList(String category,int count) {
        RxAction action = newRxAction(GET_PRODUCT_LIST);
        postHttpAction(action, mGanApi.getProductList(
                category,
                count));
    }
}
