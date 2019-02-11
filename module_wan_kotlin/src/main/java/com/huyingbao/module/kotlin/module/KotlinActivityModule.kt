package com.huyingbao.module.kotlin.module

import androidx.lifecycle.ViewModel
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.core.arch.store.RxStoreKey
import com.huyingbao.module.kotlin.ui.article.store.ArticleStore
import com.huyingbao.module.kotlin.ui.article.view.ArticleActivity
import com.huyingbao.module.kotlin.ui.friend.store.FriendStore
import com.huyingbao.module.kotlin.ui.login.store.LoginStore
import com.huyingbao.module.kotlin.ui.login.view.LoginActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module
abstract class KotlinActivityModule {

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(ArticleStore::class)
    internal abstract fun bindArticleStore(articleStore: ArticleStore): ViewModel

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(KotlinFragmentModule::class))
    internal abstract fun injectArticleActivity(): ArticleActivity

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(LoginStore::class)
    internal abstract fun bindLoginStore(loginStore: LoginStore): ViewModel

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(KotlinFragmentModule::class))
    internal abstract fun injectLoginActivity(): LoginActivity

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(FriendStore::class)
    internal abstract fun bindFriendStore(friendStore: FriendStore): ViewModel
}
