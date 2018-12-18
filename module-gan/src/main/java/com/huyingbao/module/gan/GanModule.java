package com.huyingbao.module.gan;

import com.google.gson.GsonBuilder;
import com.huyingbao.core.scope.ActivityScope;
import com.huyingbao.core.store.RxStore;
import com.huyingbao.core.store.RxStoreKey;
import com.huyingbao.module.gan.action.MainApi;
import com.huyingbao.module.gan.ui.random.RandomActivity;
import com.huyingbao.module.gan.ui.random.module.RandomActivityModule;
import com.huyingbao.module.gan.ui.random.module.RandomStore;

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
public abstract class GanModule {
    @Singleton
    @Provides
    static MainApi provideMainApi(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gank.io/api/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit.create(MainApi.class);
    }

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(RandomStore.class)
    abstract RxStore provideMainStore(RandomStore randomStore);


    @ActivityScope
    @ContributesAndroidInjector(modules = RandomActivityModule.class)
    abstract RandomActivity injectMainActivity();
}
