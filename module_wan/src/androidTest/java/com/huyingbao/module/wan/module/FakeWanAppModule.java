package com.huyingbao.module.wan.module;

import com.google.gson.GsonBuilder;
import com.huyingbao.module.wan.action.WanApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author liujunfeng
 * @date 2019/1/1
 */
@Module(includes = {WanActivityModule.class, WanStoreModule.class})
public class FakeWanAppModule {
    @Singleton
    @Provides
    WanApi provideMainApi(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit.create(WanApi.class);
    }
}
