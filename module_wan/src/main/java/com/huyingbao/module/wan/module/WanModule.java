package com.huyingbao.module.wan.module;

import com.google.gson.GsonBuilder;
import com.huyingbao.core.arch.scope.ActivityScope;
import com.huyingbao.core.arch.store.RxStore;
import com.huyingbao.core.arch.store.RxStoreKey;
import com.huyingbao.module.wan.action.WanApi;
import com.huyingbao.module.wan.ui.module.GitStore;
import com.huyingbao.module.wan.ui.view.GitActivity;

import javax.inject.Singleton;

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
    @RxStoreKey(GitStore.class)
    abstract RxStore bindGitStore(GitStore gitStore);

    @ActivityScope
    @ContributesAndroidInjector(modules = WanActivityModule.class)
    abstract GitActivity injectGitActivity();
}