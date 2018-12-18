package com.huyingbao.module.gan;

import android.app.Application;

import com.huyingbao.core.module.CommonModule;
import com.huyingbao.module.gan.action.GanModule;

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
public interface GanComponent extends AndroidInjector<GanApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        GanComponent.Builder application(Application application);

        GanComponent build();
    }
}
