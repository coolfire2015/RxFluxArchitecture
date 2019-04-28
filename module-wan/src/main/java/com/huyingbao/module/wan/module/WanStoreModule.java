package com.huyingbao.module.wan.module;

/**
 * Created by liujunfeng on 2019/1/1.
 */

import androidx.lifecycle.ViewModel;

import com.huyingbao.core.arch.store.RxStoreKey;
import com.huyingbao.module.wan.ui.article.store.ArticleStore;
import com.huyingbao.module.wan.ui.friend.store.FriendStore;
import com.huyingbao.module.wan.ui.login.store.LoginStore;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class WanStoreModule {
    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(ArticleStore.class)
    abstract ViewModel bindArticleStore(ArticleStore articleStore);

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(LoginStore.class)
    abstract ViewModel bindLoginStore(LoginStore loginStore);

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(FriendStore.class)
    abstract ViewModel bindFriendStore(FriendStore friendStore);
}
