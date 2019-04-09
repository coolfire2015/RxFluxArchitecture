package com.huyingbao.module.gan.module;

import com.google.gson.GsonBuilder;
import com.huyingbao.module.gan.action.GanApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Module其实是一个简单工厂模式，Module里面的方法基本都是创建类实例的方法。
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
@Module(includes = {GanInjectActivityModule.class, GanStoreModule.class})
public class GanAppModule {
    @Singleton
    @Provides
    GanApi provideMainApi(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gank.io/api/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit.create(GanApi.class);
    }
}
