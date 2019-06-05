package com.huyingbao.module.wan.app;

import android.app.Application;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.huyingbao.core.annotations.RxAppObserver;
import com.huyingbao.core.arch.RxAppLifecycle;
import com.huyingbao.module.wan.WanEventBusIndex;
import com.huyingbao.module.wan.ui.friend.model.WebSite;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

@RxAppObserver
public class WanAppLifecycle extends RxAppLifecycle {
    @Inject
    WebSite mWebSite;

    public WanAppLifecycle(Application application) {
        super(application);
        DaggerWanComponet.create().inject(this);
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        EventBus.builder()
                .addIndex(new WanEventBusIndex())
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
