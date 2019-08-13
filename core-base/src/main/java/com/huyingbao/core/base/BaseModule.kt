package com.huyingbao.core.base

import com.huyingbao.core.arch.module.RxFluxModule
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * 通用依赖注入仓库
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Module(includes = [
    RxFluxModule::class,
    AndroidInjectionModule::class
])
class BaseModule {
    @Singleton
    @Provides
    fun provideClientBuilder(): OkHttpClient.Builder {
        //设置日志拦截器
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return OkHttpClient.Builder()
                .connectTimeout(BaseContants.Config.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(BaseContants.Config.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(BaseContants.Config.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
    }
}