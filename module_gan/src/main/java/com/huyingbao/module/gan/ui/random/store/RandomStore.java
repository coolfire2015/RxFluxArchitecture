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

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.MutableLiveData;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Singleton
public class RandomStore extends RxStore {
//    @Inject
//    RandomActionCreator mActionCreator;
    private final MutableLiveData<Integer> mPage = new MutableLiveData<>();
    private final MutableLiveData<GanResponse<Product>> mProductList = new MutableLiveData<>();
//    public LiveData<GanResponse<Product>> mProductTrans;
    private String mCategory;

    @Inject
    RandomStore(RxDispatcher rxDispatcher) {
        super(rxDispatcher);
//        mProductTrans = Transformations.switchMap(mPage, page -> {
//            if (page != null) mActionCreator.getProductList(getCategory(), 20, 1);
//            return mProductList;
//        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mPage.setValue(null);
        mProductList.setValue(null);
    }

    /**
     * 接收接口返回的数据
     *
     * @param action
     */
    @Subscribe(tags = {@Tag(RandomActions.GET_PRODUCT_LIST)})
    public void receiveProductList(RxAction action) {
        mProductList.setValue(action.get(RxActionCreator.RESPONSE));
    }

    public void setPage(int page) {
        if (mPage.getValue() != null && page == mPage.getValue()) return;
        mPage.setValue(page);
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
