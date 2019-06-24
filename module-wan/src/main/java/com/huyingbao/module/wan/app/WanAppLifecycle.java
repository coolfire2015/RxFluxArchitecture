package com.huyingbao.module.wan.app;

import android.app.Application;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.huyingbao.core.annotations.RxAppObserver;
import com.huyingbao.core.arch.RxAppLifecycle;
import com.huyingbao.core.arch.utils.RxAndroidInjection;
import com.huyingbao.module.wan.WanEventBusIndex;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

@RxAppObserver
public class WanAppLifecycle extends RxAppLifecycle {
    @Inject
    WanAppStore mWanAppStore;

    public WanAppLifecycle(Application application) {
        super(application);
        RxAndroidInjection.INSTANCE.inject(this, application);
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        EventBus.builder()
                .addIndex(new WanEventBusIndex())
                .eventInheritance(false);
        mWanAppStore.subscribe();
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onLowMemory() {

    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onTerminate() {
        mWanAppStore.unsubscribe();
    }
}
