package com.huyingbao.core.arch.utils;

import android.app.Application;

import androidx.annotation.NonNull;

import dagger.android.AndroidInjector;
import dagger.android.HasAndroidInjector;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public final class RxAndroidInjection {
    /**
     * 公用Dagger.Android依赖注入方法
     *
     * @param object      需要完成依赖注入的类
     * @param application 实现{@link HasAndroidInjector}的Application
     */
    public static void inject(@NonNull Object object, @NonNull Application application) {
        AndroidInjector<Object> injector;
        if (application instanceof HasAndroidInjector) {
            injector = ((HasAndroidInjector) application).androidInjector();
            checkNotNull(injector, "%s.androidInjector() returned null", application.getClass());
        } else {
            throw new RuntimeException(
                    String.format(
                            "%s does not implement %s",
                            application.getClass().getCanonicalName(),
                            HasAndroidInjector.class.getCanonicalName()));
        }
        injector.inject(object);
    }

    private RxAndroidInjection() {
    }
}
