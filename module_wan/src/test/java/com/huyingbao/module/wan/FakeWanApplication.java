package com.huyingbao.module.wan;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * @author liujunfeng
 * @date 2019/1/1
 */
public class FakeWanApplication extends WanApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerWanComponent.builder().application(this).build();
    }
}
