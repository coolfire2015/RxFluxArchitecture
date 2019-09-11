package com.huyingbao.module.first.app

import androidx.lifecycle.ViewModel
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.core.arch.store.RxStoreKey
import com.huyingbao.module.common.app.CommonAppModule
import com.huyingbao.module.first.BuildConfig
import com.huyingbao.module.first.ui.first.module.FirstActivityModule
import com.huyingbao.module.first.ui.first.store.FirstStore
import com.huyingbao.module.first.ui.first.view.FirstActivity
import com.huyingbao.module.first.ui.test.module.TestActivityModule
import com.huyingbao.module.first.ui.test.store.ContentStore
import com.huyingbao.module.first.ui.test.store.TestStore
import com.huyingbao.module.first.ui.test.view.TestActivity
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
abstract class FirstAppModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun injectFirstAppLifecycle(): FirstAppLifecycle

    @ActivityScope
    @ContributesAndroidInjector(modules = [FirstActivityModule::class])
    abstract fun injectFristActivity(): FirstActivity

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(FirstStore::class)
    abstract fun bindFirstStore(firstStore: FirstStore): ViewModel

    @ActivityScope
    @ContributesAndroidInjector(modules = [TestActivityModule::class])
    abstract fun injectTestActivity(): TestActivity

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(TestStore::class)
    abstract fun bindTestStore(testStore: TestStore): ViewModel

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(ContentStore::class)
    abstract fun bindContentStore(contentStore: ContentStore): ViewModel


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
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(builder.build())
                    .build()
        }
    }
}
