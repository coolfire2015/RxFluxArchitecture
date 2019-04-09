package com.huyingbao.module.wan.ui.article.action;

import com.huyingbao.core.arch.action.RxActionManager;
import com.huyingbao.core.arch.dispatcher.RxDispatcher;
import com.huyingbao.module.wan.module.MockDaggerRule;
import com.huyingbao.module.wan.module.MockUtils;
import com.huyingbao.test.junit.RxJavaRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by liujunfeng on 2019/3/27.
 */
public class ArticleActionCreatorTest {
    @Mock
    private RxDispatcher mRxDispatcher;
    @Mock
    private RxActionManager mRxActionManager;

    @Rule
    public RxJavaRule mRxJavaRule = new RxJavaRule();
    @Rule
    public MockitoRule mMockitoRule = MockitoJUnit.rule();
    @Rule
    public MockDaggerRule mMockDaggerRule = new MockDaggerRule();

    private ArticleActionCreator mActionCreator;

    @Before
    public void setUp() {
        mActionCreator = new ArticleActionCreator(mRxDispatcher, mRxActionManager, MockUtils.getComponent().getWanApi());
    }

    @Test
    public void testGetArticleList() {
        mActionCreator.getArticleList(1);
        //调用方法成功,发送一次RxAction
        verify(mRxDispatcher).postRxAction(any());
        //调用方法成功,发送两次RxLoading
        verify(mRxDispatcher, times(2)).postRxLoading(any());
    }

    @Test
    public void testGetBannerList() {
        mActionCreator.getBannerList();
        //调用方法成功,发送一次RxAction
        verify(mRxDispatcher).postRxAction(any());
    }
}