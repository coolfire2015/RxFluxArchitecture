package com.huyingbao.module.gan.ui.random.store;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.google.common.base.Objects;
import com.huyingbao.core.action.RxAction;
import com.huyingbao.core.action.RxActionCreator;
import com.huyingbao.core.dispatcher.Dispatcher;
import com.huyingbao.core.store.RxStore;
import com.huyingbao.core.store.RxStoreChange;
import com.huyingbao.module.gan.action.GanResponse;
import com.huyingbao.module.gan.ui.random.action.RandomActionCreator;
import com.huyingbao.module.gan.ui.random.action.RandomActions;
import com.huyingbao.module.gan.ui.random.model.Product;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Singleton

public class RandomStore extends RxStore {
    @Inject
    RandomActionCreator mActionCreator;

    public LiveData<GanResponse<Product>> mProductTrans;
    private final MutableLiveData<Integer> mPage = new MutableLiveData<>();
    private final MutableLiveData<GanResponse<Product>> mProductList = new MutableLiveData<>();

    @Inject
    RandomStore(Dispatcher dispatcher) {
        super(dispatcher);
        mProductTrans = Transformations.switchMap(mPage, page -> {
            if (page != null) mActionCreator.getProductList(page);
            return mProductList;
        });
    }

    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            case RandomActions.GET_PRODUCT_LIST:
                mProductList.setValue(action.get(RxActionCreator.RESPONSE));
                return;
            case RandomActions.TO_GITHUB:
            case RandomActions.TO_PRODUCT_LIST:
            case RandomActions.TO_SHOP:
                break;
            default://此处不能省略，不是本模块的逻辑，直接返回，不发送RxStoreChange
                return;
        }
        postChange(new RxStoreChange(getClass().getSimpleName(), action));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mPage.setValue(null);
        mProductList.setValue(null);
    }

    public void setPage(int page) {
        if (Objects.equal(this.mPage.getValue(), page)) return;
        this.mPage.setValue(page);
    }

    public void refresh() {
        this.mPage.setValue(0);
    }
}
