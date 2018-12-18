package com.huyingbao.module.git.action;

import com.google.gson.GsonBuilder;
import com.huyingbao.core.scope.ActivityScope;
import com.huyingbao.core.store.RxStore;
import com.huyingbao.core.store.RxStoreKey;
import com.huyingbao.module.git.ui.GitActivity;
import com.huyingbao.module.git.ui.module.GitActivityModule;
import com.huyingbao.module.git.ui.module.GitStore;

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
public abstract class GitModule {
    @Singleton
    @Provides
    static GitApi provideMainApi(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit.create(GitApi.class);
    }

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(GitStore.class)
    abstract RxStore provideGitStore(GitStore gitStore);

    @ActivityScope
    @ContributesAndroidInjector(modules = GitActivityModule.class)
    abstract GitActivity injectGitActivity();
}
