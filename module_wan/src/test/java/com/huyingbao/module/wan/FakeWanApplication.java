package com.huyingbao.module.wan;

import org.robolectric.TestLifecycleApplication;

import java.lang.reflect.Method;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * @author liujunfeng
 * @date 2019/1/1
 */
public class FakeWanApplication extends WanApplication implements TestLifecycleApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerWanComponent.builder().application(this).build();
    }

    @Override
    public void beforeTest(Method method) {

    }

    @Override
    public void prepareTest(Object test) {

    }

    @Override
    public void afterTest(Method method) {

    }
}
