package com.huyingbao.module.gan.ui.random.store;

import com.huyingbao.core.arch.action.RxActionCreator;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.store.RxStore;
import com.huyingbao.module.gan.action.GanResponse;
import com.huyingbao.module.gan.ui.random.action.RandomActions;
import com.huyingbao.module.gan.ui.random.model.Product;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.MutableLiveData;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Singleton
public class RandomStore extends RxStore {
    private MutableLiveData<GanResponse<Product>> mProductList = new MutableLiveData<>();
    private boolean mIsCreated;
    private String mCategory;

    @Inject
    RandomStore(RxDispatcher rxDispatcher) {
        super(rxDispatcher);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mProductList.setValue(null);
        Logger.e("store cleared");
    }

    /**
     * 接收接口返回的数据
     *
     * @param action
     */
    @Subscribe(tags = {@Tag(RandomActions.GET_PRODUCT_LIST)})
    public void receiveProductList(RxAction action) {
        mIsCreated = true;
        mProductList.setValue(action.get(RxActionCreator.RESPONSE));
    }

    public MutableLiveData<GanResponse<Product>> getProductList() {
        return mProductList;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String stringExtra) {
        mCategory = stringExtra;
    }

    public boolean isCreated() {
        return mIsCreated;
    }
}
