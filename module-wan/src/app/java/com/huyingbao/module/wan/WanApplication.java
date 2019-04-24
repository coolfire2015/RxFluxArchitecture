package com.huyingbao.module.wan;


import com.huyingbao.core.base.BaseApp;

import org.greenrobot.eventbus.EventBus;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public class WanApplication extends BaseApp {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerWanComponent.builder().application(this).build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.builder()
                .addIndex(new WanEventBusIndex())
                .eventInheritance(false)
                .installDefaultEventBus();
    }
}
