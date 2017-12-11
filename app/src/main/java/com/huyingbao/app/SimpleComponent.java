package com.huyingbao.app;

import android.app.Application;

import com.huyingbao.core.module.CustomModule;
import com.huyingbao.module.main.MainModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Singleton
@Component(modules = {
        MainModule.class,
        CustomModule.class,
        AndroidSupportInjectionModule.class})
public interface SimpleComponent extends AndroidInjector<SimpleApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        SimpleComponent.Builder application(Application application);

        SimpleComponent build();
    }
}
