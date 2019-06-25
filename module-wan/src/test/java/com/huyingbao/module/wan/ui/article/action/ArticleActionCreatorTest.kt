package com.huyingbao.module.wan.ui.article.action

import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.module.wan.module.MockDaggerRule
import com.huyingbao.module.wan.module.MockUtils
import com.huyingbao.module.wan.ui.article.store.ArticleStore
import com.huyingbao.test.utils.RxJavaRule

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.verify

/**
 * Created by liujunfeng on 2019/3/27.
 */
class ArticleActionCreatorTest {
    @Rule
    var mRxJavaRule = RxJavaRule()
    @Rule
    var mMockitoRule = MockitoJUnit.rule()
    @Rule
    var mMockDaggerRule = MockDaggerRule()

    @Mock
    private val mArticleStore: ArticleStore? = null
    @Spy
    private val mRxDispatcher: RxDispatcher? = null
    @Spy
    private val mRxActionManager: RxActionManager? = null
    private var mActionCreator: ArticleActionCreator? = null

    @Before
    fun setUp() {
        mActionCreator = ArticleActionCreator(mRxDispatcher, mRxActionManager, MockUtils.component!!.wanApi)
        //注册订阅
        mRxDispatcher!!.subscribeRxStore(mArticleStore!!)
    }

    @After
    fun tearDown() {
        //取消订阅
        mRxDispatcher!!.unsubscribeRxStore(mArticleStore!!)
    }

    @Test
    fun testGetArticleList() {
        mActionCreator!!.getArticleList(1)
        verify(mArticleStore).setArticleLiveData(any())
    }

    @Test
    fun testGetBannerList() {
        mActionCreator!!.getBannerList()
        verify(mArticleStore).setBannerLiveData(any())
    }
}