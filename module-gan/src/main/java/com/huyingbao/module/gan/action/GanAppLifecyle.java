package com.huyingbao.module.gan.action;

import android.app.Application;

import androidx.annotation.NonNull;

import com.huyingbao.core.annotations.RxAppDelegate;
import com.huyingbao.core.arch.app.RxAppLifecycle;
import com.huyingbao.module.gan.GanEventBusIndex;

import org.greenrobot.eventbus.EventBus;

@RxAppDelegate
public class GanAppLifecyle implements RxAppLifecycle {
    @Override
    public void onCreate(@NonNull Application application) {
        EventBus.builder()
                .addIndex(new GanEventBusIndex())
                .eventInheritance(false)
                .installDefaultEventBus();
    }
}
