package com.huyingbao.module.wan.kotlin.module

import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.wan.kotlin.ui.article.view.ArticleActivity
import com.huyingbao.module.wan.kotlin.ui.login.view.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module
abstract class WanInjectActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [WanInjectFragmentModule::class])
    internal abstract fun injectArticleActivity(): ArticleActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [WanInjectFragmentModule::class])
    internal abstract fun injectLoginActivity(): LoginActivity
}
