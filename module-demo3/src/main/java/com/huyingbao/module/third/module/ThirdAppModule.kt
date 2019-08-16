package com.huyingbao.module.third.module

import com.google.gson.GsonBuilder
import com.huyingbao.module.common.app.BaseModule
import com.huyingbao.module.third.BuildConfig
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
    ThirdInjectActivityModule::class,
    ThirdStoreModule::class,
    com.huyingbao.module.common.app.BaseModule::class
])
class ThirdAppModule {
    //模块化App中，依赖注入仓库中会有多个方法提供Retrofit对象，
    //使用@Name注解，不同模块使用对应模块内的Retrofit对象提供方法。
    @Singleton
    @Provides
    @Named(BuildConfig.MODULE_NAME)
    fun provideRetrofit(builder: OkHttpClient.Builder): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .build()
    }
}