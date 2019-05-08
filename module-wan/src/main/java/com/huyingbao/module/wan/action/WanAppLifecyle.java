package com.huyingbao.module.wan.action;

import android.app.Application;

import com.huyingbao.core.annotations.RxAppDelegate;
import com.huyingbao.core.arch.RxAppLifecycle;
import com.huyingbao.module.wan.WanEventBusIndex;

import org.greenrobot.eventbus.EventBus;

@RxAppDelegate
public class WanAppLifecyle implements RxAppLifecycle {
    @Override
    public void onCreate(Application application) {
        EventBus.builder()
                .addIndex(new WanEventBusIndex())
                .eventInheritance(false);
    }
}
