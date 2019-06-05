package com.huyingbao.module.github.module

import com.google.gson.GsonBuilder
import com.huyingbao.core.common.module.CommonContants
import com.huyingbao.module.github.api.ReposApi
import com.huyingbao.module.github.api.UserApi
import com.huyingbao.module.github.app.GithubContants
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
@Module(includes = [GithubInjectActivityModule::class, GithubStoreModule::class])
class GithubAppModule {
    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(GithubContants.Url.BASE_API)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
    }

    @Singleton
    @Provides
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun provideReposApi(retrofit: Retrofit): ReposApi {
        return retrofit.create(ReposApi::class.java)
    }
}
