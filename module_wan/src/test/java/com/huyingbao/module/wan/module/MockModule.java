package com.huyingbao.module.wan.module;

import com.google.gson.GsonBuilder;
import com.huyingbao.module.wan.action.WanApi;
import com.huyingbao.module.wan.ui.article.action.ArticleAction;

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
 * @author liujunfeng
 * @date 2019/1/1
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
        initMockServer();
        //实例化retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RESTMockServer.getUrl())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(WanApi.class);
    }

    private void initMockServer() {
        //开启RestMockServer
        RESTMockServerStarter.startSync(new JVMFileParser());
        RESTMockServer.whenGET(RequestMatchers.pathContains(ArticleAction.GET_ARTICLE_LIST))
                .thenReturnFile(200, "json/articleList.json");
    }
}
