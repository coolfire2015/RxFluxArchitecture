package com.huyingbao.module.wan.module;

import com.google.gson.GsonBuilder;
import com.huyingbao.module.wan.BuildConfig;
import com.huyingbao.module.wan.action.WanApi;
import com.huyingbao.module.wan.action.WanContants;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.appflate.restmock.JVMFileParser;
import io.appflate.restmock.RESTMockServer;
import io.appflate.restmock.RESTMockServerStarter;
import io.appflate.restmock.utils.RequestMatchers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module
public class MockModule {
    @Singleton
    @Provides
    public OkHttpClient provideClient() {
        //实例化okhttp
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }

    @Singleton
    @Provides
    public WanApi provideWanApi(OkHttpClient okHttpClient) {
        //实例化retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(initBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(WanApi.class);
    }

    /**
     * 初始化根Url
     *
     * @return
     */
    private String initBaseUrl() {
        return BuildConfig.MOCK_URL ? initMockServer() : WanContants.BASE_URL;
    }

    /**
     * 使用RESTMockServer,为需要测试的接口提供mock数据
     *
     * @return mock的url
     */
    private String initMockServer() {
        //开启RestMockServer
        RESTMockServerStarter.startSync(new JVMFileParser());
        //article/list
        RESTMockServer.whenGET(RequestMatchers.pathContains("article/list"))
                .thenReturnFile(200, "json/articleList.json");
        //banner/json
        RESTMockServer.whenGET(RequestMatchers.pathContains("banner/json"))
                .thenReturnFile(200, "json/bannerList.json");
        //login
        RESTMockServer.whenPOST(RequestMatchers.pathContains("user/login"))
                .thenReturnFile(200, "json/login.json");
        //register
        RESTMockServer.whenPOST(RequestMatchers.pathContains("user/register"))
                .thenReturnFile(200, "json/register.json");
        //返回Mock的Url
        return RESTMockServer.getUrl();
    }
}
