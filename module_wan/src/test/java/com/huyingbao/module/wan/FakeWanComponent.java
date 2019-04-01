package com.huyingbao.module.wan;

import android.app.Application;

import com.huyingbao.core.common.module.CommonModule;
import com.huyingbao.module.wan.module.WanAppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * @author liujunfeng
 * @date 2019/1/1
 */
@Singleton
@Component(modules = {
        WanAppModule.class,
        CommonModule.class,
        AndroidSupportInjectionModule.class})
public interface FakeWanComponent extends AndroidInjector<FakeWanApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        FakeWanComponent.Builder application(Application application);

        FakeWanComponent build();
    }
}
