package com.huyingbao.module.first.module


import com.huyingbao.core.arch.scope.FragmentScope
import com.huyingbao.module.first.ui.view.FirstFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module
abstract class FirstInjectFragmentModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectFirstFragment(): FirstFragment
}
