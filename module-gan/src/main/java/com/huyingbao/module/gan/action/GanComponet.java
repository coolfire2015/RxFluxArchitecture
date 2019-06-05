package com.huyingbao.module.gan.action;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Component(modules = {AndroidInjectionModule.class})
public interface GanComponet extends AndroidInjector<GanAppLifecycle> {
}
