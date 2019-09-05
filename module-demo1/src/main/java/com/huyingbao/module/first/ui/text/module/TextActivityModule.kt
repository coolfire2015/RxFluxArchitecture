package com.huyingbao.module.first.ui.text.module

import com.huyingbao.core.arch.scope.FragmentScope
import com.huyingbao.module.first.ui.text.view.TextFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TextActivityModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectTextFragment(): TextFragment
}

