package com.huyingbao.module.gan.action;

import android.app.Application;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.huyingbao.core.annotations.RxAppObserver;
import com.huyingbao.core.arch.RxAppLifecycle;
import com.huyingbao.module.gan.GanEventBusIndex;

import org.greenrobot.eventbus.EventBus;

@RxAppObserver
public class GanAppLifecycle extends RxAppLifecycle {
    public GanAppLifecycle(Application application) {
        super(application);
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        EventBus.builder()
                .addIndex(new GanEventBusIndex())
                .eventInheritance(false);
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onLowMemory() {

    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onTerminate() {

    }
}
