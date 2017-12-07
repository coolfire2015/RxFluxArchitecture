package com.huyingbao.core.module;

import com.huyingbao.core.okhttp.HttpInterceptor;
import com.huyingbao.core.okhttp.PersistentCookieStore;

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

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Module(includes = BaseModule.class)
public abstract class CustomModule {
    @Singleton
    @Provides
    static OkHttpClient provideClient(CookieJar cookieJar, HttpInterceptor interceptor) {
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
    static CookieJar provideCookieJar(PersistentCookieStore cookieStore) {
        CookieJar cookieJar = new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                if (cookies != null && cookies.size() > 0)
                    for (Cookie item : cookies)
                        cookieStore.add(url, item);
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

