package com.huyingbao.module.wan.ui.article.action;

import com.google.gson.GsonBuilder;
import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.core.test.RxJavaTestSchedulerRule;
import com.huyingbao.module.wan.action.WanApi;

import org.junit.Before;
import org.junit.ClassRule;
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
import io.appflate.restmock.utils.RequestMatchers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.mockito.Mockito.times;

/**
 * Created by liujunfeng on 2019/3/27.
 */
public class ArticleActionCreatorTest {
    @Rule
    public MockitoRule mMockitoRule = MockitoJUnit.rule();
    @ClassRule
    public static RxJavaTestSchedulerRule mRxJavaTestSchedulerRule = new RxJavaTestSchedulerRule();
    @Spy
    private RxDispatcher mRxDispatcher;
    @Spy
    private RxActionManager mRxActionManager;

    private WanApi mWanApi;
    private ArticleActionCreator mArticleAction;

    @Before
    public void setUp() {
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
        mArticleAction = new ArticleActionCreator(mRxDispatcher, mRxActionManager, mWanApi);
    }

    @Test
    public void getArticleList() {
        RESTMockServer.whenGET(RequestMatchers.pathContains("article/list"))
                .thenReturnFile(200, "json/articleList.json");
        mArticleAction.getArticleList(1);
        //调用方法成功,发送一次RxAction
        Mockito.verify(mRxDispatcher).postRxAction(Mockito.any());
        //调用方法成功,发送两次RxLoading
        Mockito.verify(mRxDispatcher, times(2)).postRxLoading(Mockito.any());
    }

    @Test
    public void getBannerList() {
        mArticleAction.getBannerList();
        Mockito.verify(mRxDispatcher).postRxAction(Mockito.any());
    }
}