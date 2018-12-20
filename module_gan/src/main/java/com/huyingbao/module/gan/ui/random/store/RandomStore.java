package com.huyingbao.module.gan.ui.random.store;

import com.google.common.base.Objects;
import com.huyingbao.core.arch.action.RxAction;
import com.huyingbao.core.arch.action.RxActionCreator;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.store.RxStore;
import com.huyingbao.module.gan.action.GanResponse;
import com.huyingbao.module.gan.ui.random.action.RandomActionCreator;
import com.huyingbao.module.gan.ui.random.action.RandomActions;
import com.huyingbao.module.gan.ui.random.model.Product;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Singleton
public class RandomStore extends RxStore {
    private final MutableLiveData<Integer> mPage = new MutableLiveData<>();
    private final MutableLiveData<GanResponse<Product>> mProductList = new MutableLiveData<>();
    public LiveData<GanResponse<Product>> mProductTrans;
    @Inject
    RandomActionCreator mActionCreator;
    private String mCategory;

    @Inject
    RandomStore(RxDispatcher rxDispatcher) {
        super(rxDispatcher);
        mProductTrans = Transformations.switchMap(mPage, page -> {
            if (page != null) mActionCreator.getProductList(getCategory(), 20);
            return mProductList;
        });
    }

    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            case RandomActions.GET_PRODUCT_LIST:
                mProductList.setValue(action.get(RxActionCreator.RESPONSE));
                return;
            default://此处不能省略，不是本模块的逻辑，直接返回，不发送RxStoreChange
                return;
        }
        //postChange(new RxStoreChange(getClass().getSimpleName(), action));
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

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String stringExtra) {
        mCategory = stringExtra;
    }
}
