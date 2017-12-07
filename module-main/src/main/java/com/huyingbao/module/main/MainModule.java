package com.huyingbao.module.main;

import com.google.gson.GsonBuilder;
import com.huyingbao.core.scope.PerActivity;
import com.huyingbao.core.store.RxStore;
import com.huyingbao.core.store.RxStoreKey;
import com.huyingbao.module.main.action.MainApi;
import com.huyingbao.module.main.ui.main.MainActivity;
import com.huyingbao.module.main.ui.main.module.MainActivityModule;
import com.huyingbao.module.main.ui.main.module.MainStore;
import com.huyingbao.module.main.ui.shop.ShopActivity;
import com.huyingbao.module.main.ui.shop.module.ShopActivityModule;

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
public abstract class MainModule {
    @Singleton
    @Provides
    static MainApi provideMainApi(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://13.124.215.205:1337/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit.create(MainApi.class);
    }

    @Binds
    @IntoMap
    @RxStoreKey(MainStore.class)
    abstract RxStore provideRxStore(MainStore mainStore);

    @PerActivity
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity injectMainActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = ShopActivityModule.class)
    abstract ShopActivity injectShopActivity();
}
