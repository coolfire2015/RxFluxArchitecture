package com.huyingbao.module.wan.module

/**
 * Created by liujunfeng on 2019/1/1.
 */

import androidx.lifecycle.ViewModel
import com.huyingbao.core.arch.store.RxStoreKey
import com.huyingbao.module.wan.ui.article.store.ArticleStore
import com.huyingbao.module.wan.ui.friend.store.FriendStore
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class WanStoreModule {
    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(ArticleStore::class)
    abstract fun bindArticleStore(articleStore: ArticleStore): ViewModel

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(FriendStore::class)
    abstract fun bindFriendStore(friendStore: FriendStore): ViewModel
}
