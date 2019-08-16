package com.huyingbao.module.common.app

import com.huyingbao.core.arch.module.RxFluxModule
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.core.base.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * 基础依赖注入仓库，所有子模块中的AppModule中需要包括该类，实现Store注入和Dagger.Android注入
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Module(includes = [
    RxFluxModule::class,
    AndroidInjectionModule::class,
    CommonInjectModule::class
])
class CommonModule {
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
                .connectTimeout(CommonConstants.Config.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(CommonConstants.Config.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(CommonConstants.Config.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
    }
}

@Module
abstract class CommonInjectModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun injectCommonAppLifecycle(): CommonAppLifecycle
}