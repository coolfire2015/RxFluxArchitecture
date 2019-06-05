package com.huyingbao.module.wan.app;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {AndroidInjectionModule.class})
public interface WanComponet extends AndroidInjector<WanAppLifecycle> {
}
