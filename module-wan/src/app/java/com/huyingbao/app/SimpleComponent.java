package com.huyingbao.app;

import android.app.Application;

import com.huyingbao.core.module.CommonModule;
import com.huyingbao.module.gan.action.GanModule;
import com.huyingbao.module.git.action.GitModule;

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
        GitModule.class,
        CommonModule.class,
        AndroidSupportInjectionModule.class})
public interface SimpleComponent extends AndroidInjector<com.huyingbao.app.SimpleApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        SimpleComponent.Builder application(Application application);

        SimpleComponent build();
    }
}
