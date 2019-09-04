package com.huyingbao.module.second.app

import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.core.arch.store.RxStoreKey
import com.huyingbao.module.common.app.CommonAppModule
import com.huyingbao.module.second.BuildConfig
import com.huyingbao.module.second.ui.module.SecondActivityModule
import com.huyingbao.module.second.ui.store.SecondStore
import com.huyingbao.module.second.ui.view.SecondActivity
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
abstract class SecondAppModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun injectSecondAppLifecycle(): SecondAppLifecycle


    @ActivityScope
    @ContributesAndroidInjector(modules = [SecondActivityModule::class])
    abstract fun injectFristActivity(): SecondActivity

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(SecondStore::class)
    abstract fun bindSecondStore(secondStore: SecondStore): ViewModel

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
                    .baseUrl("http://gank.io/api/")
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(builder.build())
                    .build()
        }
    }
}