package com.huyingbao.module.github.module

import android.text.TextUtils
import com.google.gson.GsonBuilder
import com.huyingbao.core.common.module.CommonContants
import com.huyingbao.core.utils.LocalStorageUtils
import com.huyingbao.module.github.api.ReposApi
import com.huyingbao.module.github.api.UserApi
import com.huyingbao.module.github.app.GithubContants
import com.huyingbao.module.github.utils.PageInfoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * 全局仓库
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Module(includes = [GithubInjectActivityModule::class, GithubStoreModule::class])
class GithubAppModule {
    @Singleton
    @Provides
    fun provideRetrofit(localStorageUtils: LocalStorageUtils): Retrofit {
        //Head拦截器
        val headInterceptor = Interceptor { chain ->
            var request = chain.request()
            val token = localStorageUtils.getValue(CommonContants.Key.ACCESS_TOKEN, "")
            if (!TextUtils.isEmpty(token)) {
                //Header中添加Authorization token数据
                val url = request.url().toString()
                val requestBuilder = request.newBuilder()
                        .addHeader("Authorization", "token $token")
                        .url(url)
                request = requestBuilder.build()
            }
            chain.proceed(request)
        }
        //日志拦截器
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        //初始化OkHttp
        val clientBuilder = OkHttpClient.Builder()
                .connectTimeout(CommonContants.Config.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(CommonContants.Config.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(CommonContants.Config.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(headInterceptor)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(PageInfoInterceptor())
        //初始化Retrofit
        val retrofitBuilder = Retrofit.Builder()
                .baseUrl(GithubContants.Url.BASE_API)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(clientBuilder.build())
        return retrofitBuilder.build()
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
