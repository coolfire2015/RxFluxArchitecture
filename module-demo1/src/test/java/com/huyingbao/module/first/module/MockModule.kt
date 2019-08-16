package com.huyingbao.module.first.module

import com.google.gson.GsonBuilder
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * 依赖注入器,为测试代码提供方便测试的全局对象。
 *
 * Created by liujunfeng on 2019/7/1.
 */
@Singleton
@Component(modules = [MockModule::class])
interface MockComponent {
    /**
     * 提供实际创建的工具对象
     */
    val retrofit: Retrofit
}

/**
 * 依赖注入仓库
 *
 * 1.提供[org.mockito.Spy]和[org.mockito.Mock]对象
 *
 * 2.提供测试代码需要的全局对象
 */
@Module(includes = [FirstAppModule::class])
class MockModule {
    /**
     * 初始化Retrofit，覆盖[WanAppModule.provideRetrofit]方法
     *
     * @param builder 来自[CommonModule]
     */
    @Singleton
    @Provides
    fun provideRetrofit(builder: OkHttpClient.Builder): Retrofit {
        val retrofitBuilder = Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
        return retrofitBuilder.build()
    }
}
