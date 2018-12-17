package com.huyingbao.module.main.action;

import com.huyingbao.core.action.RxAction;
import com.huyingbao.core.action.RxActionCreator;
import com.huyingbao.core.dispatcher.Dispatcher;
import com.huyingbao.core.dispatcher.DisposableManager;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * rxAction创建发送管理类
 * Created by liujunfeng on 2017/12/7.
 */
@Singleton
public class MainActionCreator extends RxActionCreator implements MainActions {
    @Inject
    MainApi mMainApi;

    @Inject
    public MainActionCreator(Dispatcher dispatcher, DisposableManager disposableManager) {
        super(dispatcher, disposableManager);
    }

    @Override
    public void getProductList(int page) {
        //TODO 添加分页
        RxAction action = newRxAction(GET_PRODUCT_LIST);
        postHttpAction(action, mMainApi.getProductList());
    }

    @Override
    public void getShop(int userId) {
        RxAction action = newRxAction(GET_SHOP);
        postHttpAction(action, mMainApi.getShop(userId));
    }
}
