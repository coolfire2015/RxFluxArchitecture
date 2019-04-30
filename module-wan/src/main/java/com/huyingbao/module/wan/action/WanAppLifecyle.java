package com.huyingbao.module.wan.action;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.huyingbao.core.annotations.AppDelegate;
import com.huyingbao.core.arch.AppLifecycles;
import com.huyingbao.module.wan.WanEventBusIndex;

import org.greenrobot.eventbus.EventBus;

@AppDelegate
public class WanAppLifecyle implements AppLifecycles {
    @Override
    public void attachBaseContext(@NonNull Context base) {

    }

    @Override
    public void onCreate(@NonNull Application application) {
        EventBus.builder()
                .addIndex(new WanEventBusIndex())
                .eventInheritance(false)
                .installDefaultEventBus();
    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }
}
