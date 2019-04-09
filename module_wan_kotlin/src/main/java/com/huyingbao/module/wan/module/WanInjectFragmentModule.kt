package com.huyingbao.module.wan.module


import com.huyingbao.core.arch.scope.FragmentScope
import com.huyingbao.module.wan.ui.article.view.ArticleListFragment
import com.huyingbao.module.wan.ui.article.view.BannerFragment
import com.huyingbao.module.wan.ui.friend.view.FriendFragment
import com.huyingbao.module.wan.ui.login.view.LoginFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module
abstract class WanInjectFragmentModule {
    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun injectArticleListFragment(): ArticleListFragment

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun injectBannerFragment(): BannerFragment

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun injectFriendFragment(): FriendFragment

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun injectLoginFragment(): LoginFragment
}
