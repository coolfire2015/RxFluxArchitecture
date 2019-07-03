package com.huyingbao.module.github.module

import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.github.app.GithubAppLifecycle
import com.huyingbao.module.github.ui.login.view.LoginActivity
import com.huyingbao.module.github.ui.main.view.MainActivity
import com.huyingbao.module.github.ui.star.view.StarActivity
import com.huyingbao.module.github.ui.user.view.UserActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Dagger.Android仓库，使用[ContributesAndroidInjector]注解自动生成注入器
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Module
abstract class GithubInjectActivityModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun injectGithubAppLifecycle(): GithubAppLifecycle

    @ActivityScope
    @ContributesAndroidInjector(modules = [GithubInjectFragmentModule::class])
    abstract fun injectLoginActivity(): LoginActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [GithubInjectFragmentModule::class])
    abstract fun injectMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [GithubInjectFragmentModule::class])
    abstract fun injectUserActivity(): UserActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [GithubInjectFragmentModule::class])
    abstract fun injectStarActivity(): StarActivity
}
