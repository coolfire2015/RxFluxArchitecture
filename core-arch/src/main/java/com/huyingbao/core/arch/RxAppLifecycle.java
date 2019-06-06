package com.huyingbao.core.arch;

import android.app.Application;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * 跟随App生命周期
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class RxAppLifecycle implements LifecycleObserver {
    protected Application mApplication;

    public RxAppLifecycle(Application application) {
        mApplication = application;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public abstract void onCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public abstract void onLowMemory();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public abstract void onTerminate();
}
