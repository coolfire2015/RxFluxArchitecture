package com.huyingbao.module.git.module;

import com.google.gson.GsonBuilder;
import com.huyingbao.core.arch.scope.ActivityScope;
import com.huyingbao.core.arch.store.RxStore;
import com.huyingbao.core.arch.store.RxStoreKey;
import com.huyingbao.module.git.action.GitApi;
import com.huyingbao.module.git.ui.view.GitActivity;
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
