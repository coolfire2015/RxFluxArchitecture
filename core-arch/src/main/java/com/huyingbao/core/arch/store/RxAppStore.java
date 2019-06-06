package com.huyingbao.core.arch.store;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import com.huyingbao.core.arch.RxFlux;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxChange;


/**
 * 存储和管理Application生命周期内的数据
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class RxAppStore extends AndroidViewModel implements RxStore {
    private final RxDispatcher mRxDispatcher;

    public RxAppStore(Application application, RxDispatcher rxDispatcher) {
        super(application);
        this.mRxDispatcher = rxDispatcher;
    }

    /**
     * 在所关联对象（Application）创建时调用该方法，注册到{@link RxDispatcher}
     */
    public void subscribe() {
        if (mRxDispatcher.isSubscribe(this)) {
            return;
        }
        Log.i(RxFlux.TAG, "Subscribe RxApStore : " + getClass().getSimpleName());
        mRxDispatcher.subscribeRxStore(this);
    }

    /**
     * 在所关联对象Application销毁时，取消{@link RxDispatcher}中订阅
     */
    public void unsubscribe() {
        Log.i(RxFlux.TAG, "Unsubscribe RxApStore : " + getClass().getSimpleName());
        mRxDispatcher.unsubscribeRxStore(this);
    }

    @Override
    public void postChange(RxChange rxChange) {
        mRxDispatcher.postRxChange(rxChange);
    }
}
