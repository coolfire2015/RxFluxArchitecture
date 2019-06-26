package com.huyingbao.module.github.module

import android.text.TextUtils
import com.huyingbao.core.common.module.CommonContants
import com.huyingbao.core.common.utils.PageInfoInterceptor
import com.huyingbao.core.utils.LocalStorageUtils
import com.huyingbao.module.github.app.GithubContants
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideRetrofit(localStorageUtils: LocalStorageUtils, builder: OkHttpClient.Builder): Retrofit {
        //Head拦截器
        val headInterceptor = Interceptor {
            var request = it.request()
            val token = localStorageUtils.getValue(CommonContants.Key.ACCESS_TOKEN, "")
            if (!TextUtils.isEmpty(token)) {
                //Header中添加Authorization token数据
                val url = request.url.toString()
                val requestBuilder = request.newBuilder()
                        .addHeader("Authorization", "token $token")
                        .url(url)
                request = requestBuilder.build()
            }
            it.proceed(request)
        }
        //初始化OkHttp
        val client = builder
                .addInterceptor(headInterceptor)
                .addInterceptor(PageInfoInterceptor())
                .build()
        //初始化Retrofit
        val retrofitBuilder = Retrofit.Builder()
                .baseUrl(GithubContants.Url.BASE_API)
                //入参Body中如果有参数为空，依然序列化
                //.addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
        return retrofitBuilder.build()
    }
}
