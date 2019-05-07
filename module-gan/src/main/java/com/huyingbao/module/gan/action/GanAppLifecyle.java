package com.huyingbao.module.gan.action;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.huyingbao.core.annotations.AppDelegate;
import com.huyingbao.core.arch.RxAppLifecycle;
import com.huyingbao.module.gan.GanEventBusIndex;

import org.greenrobot.eventbus.EventBus;

@AppDelegate
public class GanAppLifecyle implements RxAppLifecycle {
    @Override
    public void attachBaseContext(@NonNull Context base) {

    }

    @Override
    public void onCreate(@NonNull Application application) {
        EventBus.builder()
                .addIndex(new GanEventBusIndex())
                .eventInheritance(false)
                .installDefaultEventBus();
    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }
}
