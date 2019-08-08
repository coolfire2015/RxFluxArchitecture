package com.huyingbao.module.wan.module

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.gson.GsonBuilder
import com.huyingbao.module.wan.BuildConfig
import com.huyingbao.module.wan.app.WanContants
import com.huyingbao.module.wan.db.WanAppDb
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module(includes = [WanInjectActivityModule::class, WanStoreModule::class])
class WanAppModule {
    //模块化App中，依赖注入仓库中会有多个方法提供Retrofit对象，
    //使用@Name注解，不同模块使用对应模块内的Retrofit对象提供方法。
    @Singleton
    @Provides
    @Named(BuildConfig.MODULE_NAME)
    fun provideRetrofit(builder: OkHttpClient.Builder): Retrofit {
        return Retrofit.Builder()
                .baseUrl(WanContants.Base.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .build()
    }

    /**
     * 提供[RoomDatabase]单例对象，获得创建数据库的实例：
     */
    @Singleton
    @Provides
    fun provideDataBase(application: Application): WanAppDb {
        val databaseBuilder = Room.databaseBuilder(
                application,
                WanAppDb::class.java,
                WanContants.Key.DATABASE_NAME)
                //允许Room破坏性地重新创建数据库表。
                .fallbackToDestructiveMigration()
        return databaseBuilder.build()
    }
}