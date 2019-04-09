package com.huyingbao.module.wan.module;

import com.huyingbao.core.arch.scope.ActivityScope;
import com.huyingbao.module.wan.ui.article.view.ArticleActivity;
import com.huyingbao.module.wan.ui.login.view.LoginActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module
public abstract class WanInjectActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = WanInjectFragmentModule.class)
    abstract ArticleActivity injectArticleActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = WanInjectFragmentModule.class)
    abstract LoginActivity injectLoginActivity();
}
