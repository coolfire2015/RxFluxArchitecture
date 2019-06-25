package com.huyingbao.module.wan.module

import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.wan.app.WanAppLifecycle
import com.huyingbao.module.wan.ui.article.view.ArticleActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module
abstract class WanInjectActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [WanInjectFragmentModule::class])
    abstract fun injectArticleActivity(): ArticleActivity


    @ActivityScope
    @ContributesAndroidInjector
    abstract fun injectWanAppLifecycle(): WanAppLifecycle
}
