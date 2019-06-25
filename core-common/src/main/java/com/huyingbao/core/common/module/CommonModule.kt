package com.huyingbao.core.common.module

import com.huyingbao.core.arch.module.RxFluxModule
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * 通用依赖注入仓库
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Module(includes = [RxFluxModule::class])
class CommonModule {
    @Singleton
    @Provides
    fun provideClient(): OkHttpClient {
        //设置日志拦截器
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build()
    }
}

