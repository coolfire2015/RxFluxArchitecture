package com.huyingbao.module.github.module


import com.huyingbao.core.arch.scope.FragmentScope
import com.huyingbao.module.github.ui.login.view.LoginFragment
import com.huyingbao.module.github.ui.login.view.StartFragment
import com.huyingbao.module.github.ui.main.view.DynamicFragment
import com.huyingbao.module.github.ui.main.view.MineFragment
import com.huyingbao.module.github.ui.main.view.RecommendFragment
import com.huyingbao.module.github.ui.user.view.UserFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module
abstract class GithubInjectFragmentModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectLoginFragment(): LoginFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectStartFragment(): StartFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectDynamicFragment(): DynamicFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectRecommendFragment(): RecommendFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectMineFragment(): MineFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun injectUserFragment(): UserFragment
}
