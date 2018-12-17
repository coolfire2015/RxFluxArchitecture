package com.huyingbao.module.gan;

import com.google.gson.GsonBuilder;
import com.huyingbao.core.scope.ActivityScope;
import com.huyingbao.core.store.RxStore;
import com.huyingbao.core.store.RxStoreKey;
import com.huyingbao.module.gan.ui.main.GanActivity;
import com.huyingbao.module.gan.action.MainApi;
import com.huyingbao.module.gan.ui.main.module.MainActivityModule;
import com.huyingbao.module.gan.ui.main.module.MainStore;
import com.huyingbao.module.gan.ui.shop.ShopActivity;
import com.huyingbao.module.gan.ui.shop.module.ShopActivityModule;
import com.huyingbao.module.gan.ui.shop.module.ShopStore;

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
                .baseUrl("http://13.124.215.205:1337/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit.create(MainApi.class);
    }

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(MainStore.class)
    abstract RxStore provideMainStore(MainStore mainStore);

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(ShopStore.class)
    abstract RxStore provideShopStore(ShopStore shopStore);

    @ActivityScope
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract GanActivity injectMainActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = ShopActivityModule.class)
    abstract ShopActivity injectShopActivity();
}
