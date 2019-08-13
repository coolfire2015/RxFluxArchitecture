package com.huyingbao.module.second.module

import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.second.app.SecondAppLifecycle
import com.huyingbao.module.second.ui.view.SecondActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module
abstract class SecondInjectActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [SecondInjectFragmentModule::class])
    abstract fun injectFristActivity(): SecondActivity


    @ActivityScope
    @ContributesAndroidInjector
    abstract fun injectSecondAppLifecycle(): SecondAppLifecycle
}
