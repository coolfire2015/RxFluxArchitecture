package com.huyingbao.module.gan.ui.random.store;

import com.huyingbao.core.arch.action.RxActionCreator;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxAction;
import com.huyingbao.core.arch.store.RxStore;
import com.huyingbao.module.gan.action.GanResponse;
import com.huyingbao.module.gan.ui.random.action.RandomActionCreator;
import com.huyingbao.module.gan.ui.random.action.RandomActions;
import com.huyingbao.module.gan.ui.random.model.Product;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;

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

    /**
     * postChange(new RxChange(getClass().getSimpleName(), action));
     *
     * @param action
     */
    @Subscribe(tags = {@Tag(RandomActions.GET_PRODUCT_LIST)})
    public void showProductList(RxAction action) {
        mProductList.setValue(action.get(RxActionCreator.RESPONSE));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mPage.setValue(null);
        mProductList.setValue(null);
    }

    public void setPage(int page) {
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
