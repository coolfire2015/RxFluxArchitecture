package com.huyingbao.module.wan.action;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.huyingbao.core.annotations.AppDelegate;
import com.huyingbao.core.arch.RxAppLifecycle;
import com.huyingbao.module.wan.WanEventBusIndex;

import org.greenrobot.eventbus.EventBus;

@AppDelegate
public class WanAppLifecyle implements RxAppLifecycle {
    @Override
    public void onCreate(@NonNull Application application) {
        EventBus.builder()
                .addIndex(new WanEventBusIndex())
                .eventInheritance(false)
                .installDefaultEventBus();
    }
}
