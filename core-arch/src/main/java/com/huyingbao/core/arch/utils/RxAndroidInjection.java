package com.huyingbao.core.arch.utils;

import android.app.Application;

import androidx.annotation.NonNull;

import dagger.android.AndroidInjector;
import dagger.android.HasAndroidInjector;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * 公用Dagger.Android依赖注入方法
 */
public final class RxAndroidInjection {
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
