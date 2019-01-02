package com.huyingbao.module.wan.module;

import com.google.gson.GsonBuilder;
import com.huyingbao.core.arch.scope.ActivityScope;
import com.huyingbao.core.arch.store.RxStoreKey;
import com.huyingbao.module.wan.action.WanApi;
import com.huyingbao.module.wan.ui.article.store.ArticleStore;
import com.huyingbao.module.wan.ui.article.view.ArticleActivity;
import com.huyingbao.module.wan.ui.common.friend.store.FriendStore;
import com.huyingbao.module.wan.ui.login.store.LoginStore;
import com.huyingbao.module.wan.ui.login.view.LoginActivity;

import javax.inject.Singleton;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Module
public abstract class WanModule {
    @Singleton
    @Provides
    static WanApi provideMainApi(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit.create(WanApi.class);
    }

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(ArticleStore.class)
    abstract ViewModel bindArticleStore(ArticleStore articleStore);

    @ActivityScope
    @ContributesAndroidInjector(modules = WanActivityModule.class)
    abstract ArticleActivity injectArticleActivity();

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(LoginStore.class)
    abstract ViewModel bindLoginStore(LoginStore loginStore);

    @ActivityScope
    @ContributesAndroidInjector(modules = WanActivityModule.class)
    abstract LoginActivity injectLoginActivity();

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(FriendStore.class)
    abstract ViewModel bindFriendStore(FriendStore friendStore);
}
