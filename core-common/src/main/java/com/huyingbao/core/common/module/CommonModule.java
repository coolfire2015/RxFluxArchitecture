package com.huyingbao.core.common.module;

import com.huyingbao.core.arch.module.RxFluxModule;
import com.huyingbao.core.common.interceptor.CommonHttpInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module(includes = {RxFluxModule.class})
public class CommonModule {
    @Singleton
    @Provides
    OkHttpClient provideClient(CommonHttpInterceptor interceptor) {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
    }
}

