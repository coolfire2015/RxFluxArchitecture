package com.huyingbao.module.app;


import com.huyingbao.core.common.CommonApp;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public class SimpleApplication extends CommonApp {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerSimpleComponent.builder().application(this).build();
    }
}
