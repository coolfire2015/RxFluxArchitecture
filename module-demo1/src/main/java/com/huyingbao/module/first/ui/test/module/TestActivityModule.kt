package com.huyingbao.module.first.ui.test.module

import com.huyingbao.core.arch.scope.FragmentScope
import com.huyingbao.module.first.ui.test.view.TestFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TestActivityModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectTestFragment(): TestFragment
}
