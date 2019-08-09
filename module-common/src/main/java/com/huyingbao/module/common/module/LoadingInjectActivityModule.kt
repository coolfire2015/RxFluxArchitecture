package com.huyingbao.module.common.module

import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.common.app.CommonAppLifecycle
import com.huyingbao.module.common.ui.start.view.StartActivity
import com.huyingbao.module.common.ui.web.view.CommonWebActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Dagger.Android仓库，使用[ContributesAndroidInjector]注解自动生成注入器
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Module
abstract class LoadingInjectActivityModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun injectGithubAppLifecycle(): CommonAppLifecycle

    @ActivityScope
    @ContributesAndroidInjector(modules = [LoadingInjectFragmentModule::class])
    abstract fun injectStartActivity(): StartActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [LoadingInjectFragmentModule::class])
    abstract fun injectCommonWebActivity(): CommonWebActivity
}
