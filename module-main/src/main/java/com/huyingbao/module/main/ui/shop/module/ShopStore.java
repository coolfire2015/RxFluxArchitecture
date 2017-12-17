package com.huyingbao.module.main.ui.shop.module;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.huyingbao.core.action.RxAction;
import com.huyingbao.core.action.RxActionCreator;
import com.huyingbao.core.dispatcher.Dispatcher;
import com.huyingbao.core.store.RxStore;
import com.huyingbao.module.main.action.MainActionCreator;
import com.huyingbao.module.main.action.MainActions;
import com.huyingbao.module.main.ui.shop.model.Shop;

import com.google.common.base.Objects;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Singleton
public class ShopStore extends RxStore {
    public LiveData<Shop> mShopTrans;
    private final MutableLiveData<Shop> mShop = new MutableLiveData<>();
    private final MutableLiveData<Integer> mShopId = new MutableLiveData<>();

    @Inject
    MainActionCreator mActionCreator;

    @Inject
    public ShopStore(Dispatcher dispatcher) {
        super(dispatcher);
        mShopTrans = Transformations.switchMap(mShopId, shopId -> {
            mActionCreator.getShop(shopId);
            return mShop;
        });
    }

    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            case MainActions.GET_SHOP:
                mShop.setValue(action.get(RxActionCreator.RESPONSE));
                return;
            default://此处不能省略，不是本模块的逻辑，直接返回，不发送RxStoreChange
                return;
        }
        //postChange(new RxStoreChange(getClass().getSimpleName(), action));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mShop.setValue(null);
        mShopId.setValue(null);
    }

    /**
     * 对外提供方法
     *
     * @param shopId
     */
    public void setShopId(int shopId) {
        if (Objects.equal(this.mShopId.getValue(), shopId)) return;
        this.mShopId.setValue(shopId);
    }
}
