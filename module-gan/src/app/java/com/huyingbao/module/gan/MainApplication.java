package com.huyingbao.module.gan;

import com.huyingbao.core.RxFluxApp;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public class MainApplication extends RxFluxApp {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerMainComponent.builder().application(this).build();
    }
}
