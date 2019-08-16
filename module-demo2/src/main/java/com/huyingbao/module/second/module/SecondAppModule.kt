package com.huyingbao.module.second.module

import com.google.gson.GsonBuilder
import com.huyingbao.module.common.app.CommonModule
import com.huyingbao.module.second.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module(includes = [
    SecondInjectActivityModule::class,
    SecondStoreModule::class,
    CommonModule::class
])
class SecondAppModule {
    //模块化App中，依赖注入仓库中会有多个方法提供Retrofit对象，
    //使用@Name注解，不同模块使用对应模块内的Retrofit对象提供方法。
    @Singleton
    @Provides
    @Named(BuildConfig.MODULE_NAME)
    fun provideRetrofit(builder: OkHttpClient.Builder): Retrofit {
        return Retrofit.Builder()
                .baseUrl("http://gank.io/api/")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .build()
    }
}