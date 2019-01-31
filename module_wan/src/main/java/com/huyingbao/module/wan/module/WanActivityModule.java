package com.huyingbao.module.wan.module;

import com.huyingbao.core.arch.scope.ActivityScope;
import com.huyingbao.core.arch.store.RxStoreKey;
import com.huyingbao.module.wan.ui.article.store.ArticleStore;
import com.huyingbao.module.wan.ui.article.view.ArticleActivity;
import com.huyingbao.module.wan.ui.friend.store.FriendStore;
import com.huyingbao.module.wan.ui.login.store.LoginStore;
import com.huyingbao.module.wan.ui.login.view.LoginActivity;

import javax.inject.Singleton;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module
public abstract class WanActivityModule {
    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(ArticleStore.class)
    abstract ViewModel bindArticleStore(ArticleStore articleStore);

    @ActivityScope
    @ContributesAndroidInjector(modules = WanFragmentModule.class)
    abstract ArticleActivity injectArticleActivity();

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(LoginStore.class)
    abstract ViewModel bindLoginStore(LoginStore loginStore);

    @ActivityScope
    @ContributesAndroidInjector(modules = WanFragmentModule.class)
    abstract LoginActivity injectLoginActivity();

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(FriendStore.class)
    abstract ViewModel bindFriendStore(FriendStore friendStore);
}
