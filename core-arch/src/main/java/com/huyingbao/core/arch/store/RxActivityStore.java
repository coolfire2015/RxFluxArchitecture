package com.huyingbao.core.arch.store;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.huyingbao.core.arch.RxFlux;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.arch.model.RxChange;


/**
 * 继承{@link ViewModel}，在屏幕旋转或配置更改引起的Activity重建时存活下来，其持有数据可以继续使用。
 * <p>
 * 实现{@link LifecycleObserver}，关联Activity生命周期。
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class RxActivityStore extends ViewModel implements LifecycleObserver, RxStore {
    private final RxDispatcher mRxDispatcher;

    public RxActivityStore(RxDispatcher rxDispatcher) {
        this.mRxDispatcher = rxDispatcher;
    }

    /**
     * 在所关联对象（Activity）{@link Lifecycle.Event#ON_CREATE}时，注册到{@link RxDispatcher}
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void subscribe() {
        if (mRxDispatcher.isSubscribe(this)) {
            return;
        }
        Log.i(RxFlux.TAG, "Subscribe RxActivityStore : " + getClass().getSimpleName());
        mRxDispatcher.subscribeRxStore(this);
    }

    /**
     * 在所关联对象（Activity）{@link Lifecycle.Event#ON_DESTROY}时，取消{@link RxDispatcher}中订阅
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void unsubscribe() {
        Log.i(RxFlux.TAG, "Unsubscribe RxActivityStore : " + getClass().getSimpleName());
        mRxDispatcher.unsubscribeRxStore(this);
    }

    @Override
    public void postChange(RxChange rxChange) {
        mRxDispatcher.postRxChange(rxChange);
    }
}
