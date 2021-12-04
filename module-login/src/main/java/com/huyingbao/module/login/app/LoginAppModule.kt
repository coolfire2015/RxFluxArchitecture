package com.huyingbao.module.login.app

import com.huyingbao.module.login.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2021/12/4.
 */
@Module
@InstallIn(SingletonComponent::class)
object LoginAppModule {
    /**
     * Api根路径
     */
    const val BASE_API = "https://www.wanandroid.com/"

    /**
     * 提供[Retrofit]单例对象
     *
     * 每个子模块的Module中都提供[Retrofit]单例对象，使用[Named]注解，子模块提供的单例对象。
     */
    @Singleton
    @Provides
    @Named(BuildConfig.MODULE_NAME)
    fun provideRetrofit(builder: OkHttpClient.Builder): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(builder.build())
            .build()
    }
}