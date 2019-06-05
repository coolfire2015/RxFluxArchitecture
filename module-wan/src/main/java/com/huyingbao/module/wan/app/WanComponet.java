package com.huyingbao.module.wan.app;

import com.huyingbao.module.wan.api.WanApi;
import com.huyingbao.module.wan.module.WanAppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = WanAppModule.class)
public interface WanComponet {
    void inject(WanAppLifecycle wanAppLifecycle);
}
