package com.huyingbao.core.common.module;

import com.huyingbao.core.arch.module.RxFluxModule;
import com.huyingbao.core.common.interceptor.CommonHttpInterceptor;
import com.huyingbao.core.cookie.PersistentCookieStore;
import com.huyingbao.core.progress.DownloadApi;
import com.huyingbao.core.progress.ProgressInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module(includes = {RxFluxModule.class})
public class CommonModule {
    @Singleton
    @Provides
    DownloadApi provideDownloadApi(ProgressInterceptor progressInterceptor) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(progressInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.debug.com/")
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(DownloadApi.class);
    }

    @Singleton
    @Provides
    OkHttpClient provideClient(CookieJar cookieJar, CommonHttpInterceptor interceptor) {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .cookieJar(cookieJar)
                .build();
    }

    @Singleton
    @Provides
    CookieJar provideCookieJar(PersistentCookieStore cookieStore) {
        CookieJar cookieJar = new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                if (cookies.size() > 0) {
                    for (Cookie item : cookies) {
                        cookieStore.add(url, item);
                    }
                }
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url);
                return cookies != null ? cookies : new ArrayList<>();
            }
        };
        return cookieJar;
    }
}

