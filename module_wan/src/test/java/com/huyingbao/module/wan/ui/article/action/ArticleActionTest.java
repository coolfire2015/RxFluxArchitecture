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

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liujunfeng on 2019/3/27.
 */
public class ArticleActionTest {
    @Rule
    public MockitoRule mMockitoRule = MockitoJUnit.rule();
    @ClassRule
    public static RxJavaTestSchedulerRule mRxJavaTestSchedulerRule = new RxJavaTestSchedulerRule();

    @Spy
    private RxDispatcher mRxDispatcher;
    @Spy
    private RxActionManager mRxActionManager;

    private ArticleAction mArticleAction;

    @Before
    public void setUp() {
        mArticleAction = new ArticleActionCreator(mRxDispatcher, mRxActionManager);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        ((ArticleActionCreator) mArticleAction).mWanApi = retrofit.create(WanApi.class);
    }

    @Test
    public void getArticleList() {
        mArticleAction.getArticleList(1);
        Mockito.verify(mRxDispatcher).postRxAction(Mockito.any());
    }

    @Test
    public void getBannerList() {
        mArticleAction.getBannerList();
        Mockito.verify(mRxDispatcher).postRxAction(Mockito.any());
    }
}