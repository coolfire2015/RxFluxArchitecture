package com.huyingbao.module.third.module

import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.third.app.ThirdAppLifecycle
import com.huyingbao.module.third.ui.view.ThirdActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module
abstract class ThirdInjectActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [ThirdInjectFragmentModule::class])
    abstract fun injectFristActivity(): ThirdActivity


    @ActivityScope
    @ContributesAndroidInjector
    abstract fun injectThirdAppLifecycle(): ThirdAppLifecycle
}
