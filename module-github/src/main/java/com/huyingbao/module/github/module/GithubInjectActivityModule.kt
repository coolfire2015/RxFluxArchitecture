package com.huyingbao.module.github.module

import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.github.ui.login.view.LoginActivity
import com.huyingbao.module.github.ui.main.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module
abstract class GithubInjectActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [GithubInjectFragmentModule::class])
    abstract fun injectLoginActivity(): LoginActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [GithubInjectFragmentModule::class])
    abstract fun injectMainActivity(): MainActivity
}
