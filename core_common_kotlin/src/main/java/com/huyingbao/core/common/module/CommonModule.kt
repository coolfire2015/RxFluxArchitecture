package com.huyingbao.core.common.module

import com.huyingbao.core.arch.module.RxFluxModule
import com.huyingbao.core.common.okhttp.HttpInterceptor
import com.huyingbao.core.common.okhttp.PersistentCookieStore

import java.util.ArrayList
import java.util.concurrent.TimeUnit

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module(includes = [RxFluxModule::class])
object CommonModule {
    @Singleton
    @Provides
    internal fun provideClient(cookieJar: CookieJar, interceptor: HttpInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .cookieJar(cookieJar)
                .build()
    }

    @Singleton
    @Provides
    internal fun provideCookieJar(cookieStore: PersistentCookieStore): CookieJar {
        return object : CookieJar {
            override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                if (cookies.size > 0)
                    for (item in cookies)
                        cookieStore.add(url, item)
            }

            override fun loadForRequest(url: HttpUrl): List<Cookie> {
                val cookies = cookieStore.get(url)
                return cookies ?: ArrayList()
            }
        }
    }
}

