package com.huyingbao.module.wan.module

import com.google.gson.GsonBuilder
import com.huyingbao.module.wan.action.WanApi

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module(includes = [WanActivityModule::class])
class WanModule {
    @Singleton
    @Provides
    internal fun provideMainApi(client: OkHttpClient): WanApi {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://www.wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
        return retrofit.create(WanApi::class.java!!)
    }
}
