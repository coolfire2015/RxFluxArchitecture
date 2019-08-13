package com.huyingbao.module.first.module

import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.first.app.FirstAppLifecycle
import com.huyingbao.module.first.ui.view.FirstActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module
abstract class FirstInjectActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [FirstInjectFragmentModule::class])
    abstract fun injectFristActivity(): FirstActivity


    @ActivityScope
    @ContributesAndroidInjector
    abstract fun injectFirstAppLifecycle(): FirstAppLifecycle
}
