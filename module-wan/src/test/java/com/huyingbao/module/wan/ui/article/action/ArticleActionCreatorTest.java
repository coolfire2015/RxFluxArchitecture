package com.huyingbao.module.wan.ui.article.action;

import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.module.wan.module.MockDaggerRule;
import com.huyingbao.module.wan.module.MockUtils;
import com.huyingbao.module.wan.ui.article.store.ArticleStore;
import com.huyingbao.test.utils.RxJavaRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by liujunfeng on 2019/3/27.
 */
public class ArticleActionCreatorTest {
    @Rule
    public RxJavaRule mRxJavaRule = new RxJavaRule();
    @Rule
    public MockitoRule mMockitoRule = MockitoJUnit.rule();
    @Rule
    public MockDaggerRule mMockDaggerRule = new MockDaggerRule();

    @Mock
    private ArticleStore mArticleStore;
    @Spy
    private RxDispatcher mRxDispatcher;
    @Spy
    private RxActionManager mRxActionManager;
    private ArticleActionCreator mActionCreator;

    @Before
    public void setUp() {
        mActionCreator = new ArticleActionCreator(mRxDispatcher, mRxActionManager, MockUtils.getComponent().getWanApi());
        //注册订阅
        mRxDispatcher.subscribeRxStore(mArticleStore);
    }

    @After
    public void tearDown() {
        //取消订阅
        mRxDispatcher.unsubscribeRxStore(mArticleStore);
    }

    @Test
    public void testGetArticleList() {
        mActionCreator.getArticleList(1);
        verify(mArticleStore).setArticleLiveData(any());
    }

    @Test
    public void testGetBannerList() {
        mActionCreator.getBannerList();
        verify(mArticleStore).setBannerLiveData(any());
    }
}