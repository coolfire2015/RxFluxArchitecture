package com.huyingbao.module.wan;

import android.app.Application;

import com.huyingbao.core.common.module.CommonModule;
import com.huyingbao.module.wan.module.WanAppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.AndroidInjectionModule;

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
@Component(modules = {
        WanAppModule.class,
        CommonModule.class,
        AndroidInjectionModule.class})
public interface WanComponent extends AndroidInjector<WanApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        WanComponent.Builder application(Application application);

        WanComponent build();
    }
}
