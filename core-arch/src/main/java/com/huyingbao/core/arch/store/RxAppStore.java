package com.huyingbao.core.arch.store;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import com.huyingbao.core.arch.RxFlux;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxChange;


/**
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class RxAppStore extends AndroidViewModel implements RxStore {
    private final RxDispatcher mRxDispatcher;

    public RxAppStore(Application application, RxDispatcher rxDispatcher) {
        super(application);
        this.mRxDispatcher = rxDispatcher;
    }

    /**
     * 所关联对象Application创建时调用该方法
     * 需要将store注册到dispatcher中
     */
    public void subscribe() {
        if (mRxDispatcher.isSubscribe(this)) {
            return;
        }
        Log.i(RxFlux.TAG, "Subscribe RxApStore : " + getClass().getSimpleName());
        mRxDispatcher.subscribeRxStore(this);
    }

    /**
     * 所关联对象Application销毁时调用该方法
     * 从dispatcher中取消订阅
     */
    public void unsubscribe() {
        Log.i(RxFlux.TAG, "Unsubscribe RxApStore : " + getClass().getSimpleName());
        mRxDispatcher.unsubscribeRxStore(this);
    }

    /**
     * 传递更改,传递一个RxStoreChange,
     * 每一个RxStoreChange由storeId和action组成
     *
     * @param change
     */
    @Override
    public void postChange(RxChange change) {
        mRxDispatcher.postRxChange(change);
    }
}
