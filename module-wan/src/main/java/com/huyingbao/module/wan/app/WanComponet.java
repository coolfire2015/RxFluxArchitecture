package com.huyingbao.module.wan.app;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Component(modules = {AndroidInjectionModule.class})
public interface WanComponet extends AndroidInjector<WanAppLifecycle> {
}
