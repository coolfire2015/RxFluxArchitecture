package com.huyingbao.module.app;


import com.huyingbao.core.base.BaseApp;
import com.huyingbao.module.gan.GanEventBusIndex;
import com.huyingbao.module.wan.WanEventBusIndex;

import org.greenrobot.eventbus.EventBus;

import androidx.core.util.Pair;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * 主Application
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public class SimpleApplication extends BaseApp {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerSimpleComponent.builder().application(this).build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.builder()
                .addIndex(new GanEventBusIndex())
                .addIndex(new WanEventBusIndex())
                .addIndex(new com.huyingbao.module.wan.kotlin.WanEventBusIndex())
                .eventInheritance(false)
                .installDefaultEventBus();
    }
}
