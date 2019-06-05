package com.huyingbao.module.app;


import com.huyingbao.core.annotations.RxAppOwner;
import com.huyingbao.core.base.BaseApp;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * 主Application
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
@RxAppOwner
public class SimpleApplication extends BaseApp {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerSimpleComponent.builder().application(this).build();
    }
}
