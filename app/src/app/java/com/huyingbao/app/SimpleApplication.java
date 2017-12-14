package com.huyingbao.app;


import android.app.Application;

import com.huyingbao.core.RxFluxApp;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public class SimpleApplication extends RxFluxApp {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerSimpleComponent.builder().application(this).build();
    }
}
