package com.huyingbao.module.app;


import com.huyingbao.core.base.BaseApp;

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
}
