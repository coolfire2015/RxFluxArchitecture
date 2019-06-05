package com.huyingbao.module.gan;

import com.huyingbao.core.annotations.RxAppOwner;
import com.huyingbao.core.base.BaseApp;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * Created by liujunfeng on 2019/1/1.
 */
@RxAppOwner
public class GanApplication extends BaseApp {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerGanComponent.builder().application(this).build();
    }
}
