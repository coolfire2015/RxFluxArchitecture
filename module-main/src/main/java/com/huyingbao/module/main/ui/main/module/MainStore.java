package com.huyingbao.module.main.ui.main.module;

import android.arch.lifecycle.MutableLiveData;

import com.huyingbao.core.action.RxAction;
import com.huyingbao.core.action.RxActionCreator;
import com.huyingbao.core.dispatcher.Dispatcher;
import com.huyingbao.core.store.RxStore;
import com.huyingbao.core.store.RxStoreChange;
import com.huyingbao.module.main.action.MainActionCreator;
import com.huyingbao.module.main.action.MainActions;
import com.huyingbao.module.main.ui.main.model.Product;
import com.huyingbao.module.main.ui.main.model.Shop;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Singleton
public class MainStore extends RxStore {
    public final MutableLiveData<List<Product>> mProductList = new MutableLiveData<>();
    public final MutableLiveData<Shop> mShop = new MutableLiveData<>();
    @Inject
    MainActionCreator mActionCreator;

    @Inject
    public MainStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            case MainActions.GET_PRODUCT_LIST:
                mProductList.setValue(action.get(RxActionCreator.RESPONSE));
                return;
            case MainActions.GET_SHOP:
                mShop.setValue(action.get(RxActionCreator.RESPONSE));
                return;
            case MainActions.TO_GITHUB:
            case MainActions.TO_PRODUCT_LIST:
            case MainActions.TO_SHOP:
                break;
            default://此处不能省略，不是本模块的逻辑，直接返回，不发送RxStoreChange
                return;
        }
        postChange(new RxStoreChange(getClass().getSimpleName(), action));
    }

    public MutableLiveData<List<Product>> getProductList() {
        if (mProductList.getValue() == null) mActionCreator.getProductList();
        return mProductList;
    }
}
