package com.huyingbao.module.app;


import com.huyingbao.core.common.CommonApp;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * 主Application
 *
 * @author liujunfeng
 * @date 2019/1/1
 */
public class SimpleApplication extends CommonApp {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerSimpleComponent.builder().application(this).build();
    }
}
