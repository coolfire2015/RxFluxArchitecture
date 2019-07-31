package com.huyingbao.core.common.module

import androidx.lifecycle.ViewModel
import com.huyingbao.core.arch.module.RxFluxModule
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.core.arch.store.RxStoreKey
import com.huyingbao.core.common.BuildConfig
import com.huyingbao.core.common.web.store.CommonWebStore
import com.huyingbao.core.common.web.view.CommonWebActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * 通用依赖注入仓库
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Module(includes = [RxFluxModule::class, CommonInjectActivityModule::class, CommonStoreModule::class])
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
                .connectTimeout(CommonContants.Config.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(CommonContants.Config.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(CommonContants.Config.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
    }
}

@Module
abstract class CommonInjectActivityModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun injectCommonWebActivity(): CommonWebActivity
}

@Module
abstract class CommonStoreModule {
    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(CommonWebStore::class)
    abstract fun bindCommonWebStore(commonWebStore: CommonWebStore): ViewModel
}

