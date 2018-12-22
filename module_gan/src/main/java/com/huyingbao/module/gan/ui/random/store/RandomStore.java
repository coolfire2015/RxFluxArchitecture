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
 * 如果OS销毁app释放资源，用户数据不会丢失；
 * 当网络很差或者断网的时候app可以继续工作。
 * <p>
 * ViewModel的目的是获取并保存Activity或Fragment所必需的信息
 * Activity或Fragment应该能够观察到ViewModel中的变化
 * <p>
 * ViewModel对象在获取ViewModel时被限定为传递给ViewModelProvider的生命周期。
 * ViewModel保留在内存中，直到Activity销毁或Fragment分离之前。
 * <p>
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

    /**
     * 当所有者Activity销毁时,框架调用ViewModel的onCleared（）方法，以便它可以清理资源。
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        mIsCreated = false;
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
