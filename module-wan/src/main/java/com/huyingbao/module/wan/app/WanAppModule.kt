package com.huyingbao.module.wan.app

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase


import com.huyingbao.module.common.app.CommonAppModule
import com.huyingbao.module.wan.BuildConfig
import dagger.Module
import dagger.Provides

import okhttp3.OkHttpClient
import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module(includes = [CommonAppModule::class])
object WanAppModule {
    /**
     * Api根路径
     */
    const val BASE_API = "https://www.wanandroid.com/"

    /**
     * 需要创建的数据库名字
     */
    const val DATABASE_NAME = "wan-db"

    /**
     * 提供[Retrofit]单例对象
     *
     * 每个子模块的Module中都提供[Retrofit]单例对象，使用[Named]注解，子模块提供的单例对象。
     */
    @Singleton
    @Provides
    @Named(BuildConfig.MODULE_NAME)
    fun provideRetrofit(builder: OkHttpClient.Builder): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build()
    }

    /**
     * 提供[RoomDatabase]单例对象，获得创建数据库的实例：
     */
    @Singleton
    @Provides
    fun provideDataBase(application: Application): WanAppDatabase {
        val databaseBuilder = Room.databaseBuilder(
                application,
                WanAppDatabase::class.java,
                DATABASE_NAME)
                //允许Room破坏性地重新创建数据库表。
                .fallbackToDestructiveMigration()
        return databaseBuilder.build()
    }
}