package com.huyingbao.module.wan.module;

import com.google.gson.GsonBuilder;
import com.huyingbao.module.wan.api.WanApi;
import com.huyingbao.module.wan.app.WanContants;
import com.huyingbao.module.wan.ui.friend.model.WebSite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module(includes = {WanInjectActivityModule.class, WanStoreModule.class})
public class WanAppModule {
    @Singleton
    @Provides
    WanApi provideWanApi(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WanContants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit.create(WanApi.class);
    }

    @Singleton
    @Provides
    WebSite provideWebSite() {
        return new WebSite();
    }
}