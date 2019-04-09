package com.huyingbao.module.wan.module

import androidx.lifecycle.ViewModel
import com.huyingbao.core.arch.store.RxStoreKey
import com.huyingbao.module.wan.ui.article.store.ArticleStore
import com.huyingbao.module.wan.ui.friend.store.FriendStore
import com.huyingbao.module.wan.ui.login.store.LoginStore
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
