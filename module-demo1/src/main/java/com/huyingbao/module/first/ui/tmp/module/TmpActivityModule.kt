package com.huyingbao.module.first.ui.tmp.module

import com.huyingbao.core.arch.scope.FragmentScope
import com.huyingbao.module.first.ui.tmp.view.TmpFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TmpActivityModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectTmpFragment(): TmpFragment
}

