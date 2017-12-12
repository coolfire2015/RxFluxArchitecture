package com.huyingbao.module.git;

import android.app.Application;

import com.huyingbao.core.module.CustomModule;

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
        GitModule.class,
        CustomModule.class,
        AndroidSupportInjectionModule.class})
public interface GitComponent extends AndroidInjector<com.huyingbao.module.git.GitApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        GitComponent.Builder application(Application application);

        GitComponent build();
    }
}
