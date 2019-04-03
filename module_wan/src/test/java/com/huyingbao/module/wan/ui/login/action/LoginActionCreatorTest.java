package com.huyingbao.module.wan.ui.login.action;

import com.google.gson.GsonBuilder;
import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.test.RxJavaTestSchedulerRule2;
import com.huyingbao.module.wan.action.WanApi;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.concurrent.TimeUnit;

import io.appflate.restmock.JVMFileParser;
import io.appflate.restmock.RESTMockServer;
import io.appflate.restmock.RESTMockServerStarter;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liujunfeng on 2019/4/3.
 */
public class LoginActionCreatorTest {
    @Rule
    public MockitoRule mMockitoRule = MockitoJUnit.rule();
    @Rule
    public RxJavaTestSchedulerRule2 mRxJavaTestSchedulerRule = new RxJavaTestSchedulerRule2();
    @Spy
    private RxDispatcher mRxDispatcher;
    @Spy
    private RxActionManager mRxActionManager;

    private WanApi mWanApi;
    private LoginActionCreator mLoginActionCreator;

    @Before
    public void setUp() throws Exception {
        //启动服务
        RESTMockServerStarter.startSync(new JVMFileParser());
        //定义HttpClient,并添加拦截器
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();
        //设置HttpClient
        Retrofit retrofit = new Retrofit.Builder()
                //设置mock的Url地址
                .baseUrl(RESTMockServer.getUrl())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        mWanApi = retrofit.create(WanApi.class);
        mLoginActionCreator = new LoginActionCreator(mRxDispatcher, mRxActionManager, mWanApi);
    }

    @Test
    public void register() {
    }

    @Test
    public void login() {
    }

    @Test
    public void getIdentify() {
        mLoginActionCreator.getIdentify();
        //调用调度器方法,调整时间
        mRxJavaTestSchedulerRule.getTestScheduler().advanceTimeTo(10, TimeUnit.SECONDS);
        //验证方法被调用次数
        Mockito.verify(mRxDispatcher, Mockito.times(10)).postRxAction(Mockito.any());
    }
}