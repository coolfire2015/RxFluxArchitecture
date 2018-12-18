package com.huyingbao.module.main;

import android.app.Application;

import com.huyingbao.core.module.CommonModule;
import com.huyingbao.module.gan.GanModule;

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
        GanModule.class,
        CommonModule.class,
        AndroidSupportInjectionModule.class})
public interface MainComponent extends AndroidInjector<com.huyingbao.module.main.MainApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        MainComponent.Builder application(Application application);

        MainComponent build();
    }
}
