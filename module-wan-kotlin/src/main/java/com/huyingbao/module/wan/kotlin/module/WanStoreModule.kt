package com.huyingbao.module.wan.kotlin.module

import androidx.lifecycle.ViewModel
import com.huyingbao.core.arch.store.RxStoreKey
import com.huyingbao.module.wan.kotlin.ui.article.store.ArticleStore
import com.huyingbao.module.wan.kotlin.ui.friend.store.FriendStore
import com.huyingbao.module.wan.kotlin.ui.login.store.LoginStore
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module
abstract class WanStoreModule {
    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(ArticleStore::class)
    internal abstract fun bindArticleStore(articleStore: ArticleStore): ViewModel

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(LoginStore::class)
    internal abstract fun bindLoginStore(loginStore: LoginStore): ViewModel

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(FriendStore::class)
    internal abstract fun bindFriendStore(friendStore: FriendStore): ViewModel
}
