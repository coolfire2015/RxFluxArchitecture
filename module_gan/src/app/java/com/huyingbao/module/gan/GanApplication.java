package com.huyingbao.module.gan;

import com.huyingbao.core.common.CommonApp;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public class GanApplication extends CommonApp {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerGanComponent.builder().application(this).build();
    }
}
