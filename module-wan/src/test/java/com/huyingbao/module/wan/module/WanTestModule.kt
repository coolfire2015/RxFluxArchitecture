package com.huyingbao.module.wan.module

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.gson.GsonBuilder
import com.huyingbao.module.common.app.CommonAppModule
import com.huyingbao.module.wan.BuildConfig
import com.huyingbao.module.wan.app.WanAppDatabase
import com.huyingbao.module.wan.app.WanAppModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.appflate.restmock.JVMFileParser
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.RESTMockServerStarter
import io.appflate.restmock.utils.RequestMatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * 依赖注入仓库
 *
 * 1.提供[org.mockito.Spy]和[org.mockito.Mock]对象
 *
 * 2.提供测试代码需要的全局对象
 */
@Module
@InstallIn(SingletonComponent::class)
class WanTestModule {
    /**
     * 初始化Retrofit，覆盖[WanAppModule.provideRetrofit]方法
     *
     * @param builder 来自[CommonAppModule]
     */
    @Singleton
    @Provides
    @Named(BuildConfig.MODULE_NAME)
    fun provideRetrofit(builder: OkHttpClient.Builder): Retrofit {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(if (BuildConfig.MOCK_URL) initMockServer() else WanAppModule.BASE_API)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().serializeNulls().create()
                )
            )
            .client(builder.build())
        return retrofitBuilder.build()
    }

    /**
     * 提供[RoomDatabase]单例对象，获得创建内存数据库的实例，覆盖[WanAppModule.provideDataBase]方法
     */
    @Singleton
    @Provides
    fun provideDataBase(application: Application): WanAppDatabase {
        val databaseBuilder = Room.inMemoryDatabaseBuilder(
            application,
            WanAppDatabase::class.java
        )
            //允许Room破坏性地重新创建数据库表。
            .fallbackToDestructiveMigration()
        return databaseBuilder.build()
    }
}

/**
 * 使用RESTMockServer,为需要测试的接口提供mock数据
 */
fun initMockServer(): String {
    //开启RestMockServer
    RESTMockServerStarter.startSync(JVMFileParser())
    //article/list
    RESTMockServer.whenGET(RequestMatchers.pathContains("article/list"))
        .thenReturnFile(200, "json/articleList.json")
    //banner/json
    RESTMockServer.whenGET(RequestMatchers.pathContains("banner/json"))
        .thenReturnFile(200, "json/bannerList.json")
    //login
    RESTMockServer.whenPOST(RequestMatchers.pathContains("user/login"))
        .thenReturnFile(200, "json/login.json")
    //register
    RESTMockServer.whenPOST(RequestMatchers.pathContains("user/register"))
        .thenReturnFile(200, "json/register.json")
    //返回Mock的Url
    return RESTMockServer.getUrl()
}
