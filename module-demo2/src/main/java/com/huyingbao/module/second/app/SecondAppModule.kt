package com.huyingbao.module.second.app

import com.google.gson.GsonBuilder
import com.huyingbao.module.second.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module
@InstallIn(ApplicationComponent::class)
class SecondAppModule {
    @Singleton
    @Provides
    @Named(BuildConfig.MODULE_NAME)
    fun provideRetrofit(builder: OkHttpClient.Builder): Retrofit {
        return Retrofit.Builder()
                .baseUrl("http://gank.io/api/")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
                .client(builder.build())
                .build()
    }
}