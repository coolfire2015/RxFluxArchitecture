package com.huyingbao.core.okhttp;

import com.huyingbao.core.progress.DownloadApi;
import com.huyingbao.core.progress.ProgressInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@Module
public class DownloadModule {
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
}

