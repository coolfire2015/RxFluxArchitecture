package com.huyingbao.module.common.module


import com.huyingbao.core.arch.scope.FragmentScope
import com.huyingbao.module.common.ui.start.view.StartFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Dagger.Android仓库，使用[ContributesAndroidInjector]注解自动生成注入器
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Module
abstract class LoadingInjectFragmentModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectStartFragment(): StartFragment
}
