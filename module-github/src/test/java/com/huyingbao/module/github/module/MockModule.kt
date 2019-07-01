package com.huyingbao.module.github.module

import android.text.TextUtils
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.common.module.CommonContants
import com.huyingbao.module.github.BuildConfig
import com.huyingbao.module.github.app.GithubContants
import dagger.Module
import dagger.Provides
import io.appflate.restmock.JVMFileParser
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.RESTMockServerStarter
import io.appflate.restmock.utils.RequestMatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Module
class MockModule {
    @Singleton
    @Provides
    fun provideRxActionManager(): RxActionManager {
        return RxActionManager()
    }

    @Singleton
    @Provides
    fun provideRxDispatcher(): RxDispatcher {
        return RxDispatcher()
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        //Head拦截器
        val headInterceptor = Interceptor { chain ->
            var request = chain.request()
            val token = request.header(CommonContants.Header.AUTHORIZATION)
            if (TextUtils.isEmpty(token)) {
                //Header中添加Authorization token数据
                val url = request.url.toString()
                val requestBuilder = request.newBuilder()
                        .addHeader(CommonContants.Header.AUTHORIZATION, "token ccdc62e68a9c21654f1811e9f72d93265988ce29")
                        .url(url)
                request = requestBuilder.build()
            }
            chain.proceed(request)
        }
        //日志拦截器
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        //初始化OkHttp
        val clientBuilder = OkHttpClient.Builder()
                .connectTimeout(CommonContants.Config.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(CommonContants.Config.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(CommonContants.Config.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(headInterceptor)
                .addInterceptor(loggingInterceptor)
        //单元测试中不添加该拦截器
        //.addInterceptor(PageInfoInterceptor())
        //初始化Retrofit
        val retrofitBuilder = Retrofit.Builder()
                .baseUrl(GithubContants.Url.BASE_API)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(clientBuilder.build())
        return retrofitBuilder.build()
    }

    /**
     * 初始化根Url
     */
    private fun initBaseUrl(): String {
        return if (BuildConfig.MOCK_URL) initMockServer() else GithubContants.Url.BASE_API
    }

    /**
     * 使用RESTMockServer,为需要测试的接口提供mock数据
     *
     * @return mock的url
     */
    private fun initMockServer(): String {
        //开启RestMockServer
        RESTMockServerStarter.startSync(JVMFileParser())
        //article/list
        RESTMockServer.whenGET(RequestMatchers.pathContains("article/list"))
                .thenReturnFile(200, "json/articleList.json")
        //banner/json
        RESTMockServer.whenGET(RequestMatchers.pathContains("banner/json"))
                .thenReturnFile(200, "json/bannerList.json")
        //login
        RESTMockServer.whenPOST(RequestMatchers.pathContains("user/login"))
                .thenReturnFile(200, "json/login.json")
        //register
        RESTMockServer.whenPOST(RequestMatchers.pathContains("user/register"))
                .thenReturnFile(200, "json/register.json")
        //返回Mock的Url
        return RESTMockServer.getUrl()
    }
}
