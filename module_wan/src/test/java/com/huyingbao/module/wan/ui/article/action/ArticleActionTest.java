package com.huyingbao.module.wan.ui.article.action;

import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

/**
 * Created by liujunfeng on 2019/3/27.
 */
public class ArticleActionTest {
    @Rule
    public MockitoRule mMockitoRule = MockitoJUnit.rule();
    @Rule
    public RxJavaTestSchedulerRule mRxJavaTestSchedulerRule = new RxJavaTestSchedulerRule();
    @Spy
    private RxDispatcher mRxDispatcher;
    @Spy
    private RxActionManager mRxActionManager;

    private ArticleAction mArticleAction;

    @Before
    public void setUp() {
        mArticleAction = new ArticleActionCreator(mRxDispatcher, mRxActionManager);
    }

    @Test
    public void getArticleList() {
    }

    @Test
    public void getBannerList() {
        mArticleAction.getBannerList();
    }
}