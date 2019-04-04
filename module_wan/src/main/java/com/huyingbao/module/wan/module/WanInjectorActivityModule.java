package com.huyingbao.module.wan.module;

import com.huyingbao.core.arch.scope.ActivityScope;
import com.huyingbao.module.wan.ui.article.view.ArticleActivity;
import com.huyingbao.module.wan.ui.login.view.LoginActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author liujunfeng
 * @date 2019/1/1
 */
@Module
public abstract class WanInjectorActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = WanInjectorFragmentModule.class)
    abstract ArticleActivity injectArticleActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = WanInjectorFragmentModule.class)
    abstract LoginActivity injectLoginActivity();
}
