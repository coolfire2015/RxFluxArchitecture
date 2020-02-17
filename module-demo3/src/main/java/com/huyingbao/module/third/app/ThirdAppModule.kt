package com.huyingbao.module.third.app

import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.core.arch.store.RxStoreKey
import com.huyingbao.module.common.app.CommonAppModule
import com.huyingbao.module.third.BuildConfig
import com.huyingbao.module.third.ui.module.ThirdActivityModule
import com.huyingbao.module.third.ui.store.ThirdStore
import com.huyingbao.module.third.ui.view.ThirdActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module(includes = [CommonAppModule::class])
abstract class ThirdAppModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun injectThirdAppLifecycle(): ThirdAppLifecycle

    @ActivityScope
    @ContributesAndroidInjector(modules = [ThirdActivityModule::class])
    abstract fun injectThirdActivity(): ThirdActivity

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(ThirdStore::class)
    abstract fun bindThirdStore(thirdStore: ThirdStore): ViewModel

    @Module
    companion object {
        //模块化App中，依赖注入仓库中会有多个方法提供Retrofit对象，
        //使用@Name注解，不同模块使用对应模块内的Retrofit对象提供方法。
        @JvmStatic
        @Singleton
        @Provides
        @Named(BuildConfig.MODULE_NAME)
        fun provideRetrofit(builder: OkHttpClient.Builder): Retrofit {
            return Retrofit.Builder()
                    .baseUrl("https://www.wanandroid.com/")
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(builder.build())
                    .build()
        }
    }
}