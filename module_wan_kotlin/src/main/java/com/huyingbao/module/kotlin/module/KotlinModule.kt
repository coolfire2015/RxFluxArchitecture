package com.huyingbao.module.kotlin.module

import com.google.gson.GsonBuilder
import com.huyingbao.module.kotlin.action.KotlinApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module(includes = [KotlinActivityModule::class])
class KotlinModule {
    @Singleton
    @Provides
    internal fun provideMainApi(client: OkHttpClient): KotlinApi {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://www.kotlinandroid.com/")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
        return retrofit.create(KotlinApi::class.java)
    }
}
