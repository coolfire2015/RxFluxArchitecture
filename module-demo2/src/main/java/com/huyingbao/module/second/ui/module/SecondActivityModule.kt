package com.huyingbao.module.second.ui.module


import com.huyingbao.core.arch.scope.FragmentScope
import com.huyingbao.module.second.ui.view.SecondFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module
abstract class SecondActivityModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectSecondFragment(): SecondFragment
}
