package com.huyingbao.module.wan;

import android.app.Application;

import com.huyingbao.core.common.module.CommonModule;
import com.huyingbao.module.wan.module.WanModule;

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
        WanModule.class,
        CommonModule.class,
        AndroidSupportInjectionModule.class})
public interface WanComponent extends AndroidInjector<WanApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        WanComponent.Builder application(Application application);

        WanComponent build();
    }
}
