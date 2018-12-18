package com.huyingbao.module.gan.ui.random.action;

import com.huyingbao.core.action.RxAction;
import com.huyingbao.core.action.RxActionCreator;
import com.huyingbao.core.dispatcher.Dispatcher;
import com.huyingbao.core.dispatcher.DisposableManager;
import com.huyingbao.module.gan.action.MainApi;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * rxAction创建发送管理类
 * Created by liujunfeng on 2017/12/7.
 */
@Singleton
public class RandomActionCreator extends RxActionCreator implements RandomActions {
    @Inject
    MainApi mMainApi;

    @Inject
    public RandomActionCreator(Dispatcher dispatcher, DisposableManager disposableManager) {
        super(dispatcher, disposableManager);
    }

    @Override
    public void getProductList(int page) {
        RxAction action = newRxAction(GET_PRODUCT_LIST);
        postHttpAction(action, mMainApi.getProductList().delay(5, TimeUnit.SECONDS));
    }
}
