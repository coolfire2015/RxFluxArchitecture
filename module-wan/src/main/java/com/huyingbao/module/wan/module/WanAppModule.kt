package com.huyingbao.module.wan.module

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.gson.GsonBuilder
import com.huyingbao.module.wan.BuildConfig
import com.huyingbao.module.wan.app.WanContants
import com.huyingbao.module.wan.database.WanAppDatabase
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
    fun provideDataBase(application: Application): WanAppDatabase {
        val databaseBuilder = Room.databaseBuilder(
                application,
                WanAppDatabase::class.java,
                WanContants.Key.DATABASE_NAME)
                //允许Room破坏性地重新创建数据库表。
                .fallbackToDestructiveMigration()
        return databaseBuilder.build()
    }
}