package com.huyingbao.module.wan.ui.article.action

import com.huyingbao.core.test.subscriber.BaseSubscriberTest
import com.huyingbao.module.wan.module.WanMockUtils
import com.huyingbao.module.wan.module.wanMockDaggerRule
import com.huyingbao.module.wan.ui.article.store.ArticleStore
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

/**
 * 实际调用接口方法，Mock[com.huyingbao.core.arch.store.RxStore]和[com.huyingbao.core.arch.view.SubscriberView]，
 * 验证方法调用之后[com.huyingbao.core.arch.store.RxStore]和[com.huyingbao.core.arch.view.SubscriberView]得到响应。
 *
 * Created by liujunfeng on 2019/3/28.
 */
class ArticleActionCreatorTest : BaseSubscriberTest() {
    /**
     * 初始化DaggerMock
     */
    @get:Rule
    var mockDaggerRule = wanMockDaggerRule()

    private var articleStore: ArticleStore = Mockito.mock(ArticleStore::class.java)

    private var articleActionCreator: ArticleActionCreator? = null

    override fun getSubscriberList(): List<Any> {
        return listOfNotNull(articleStore)
    }

    @Before
    fun setUp() {
        articleActionCreator = ArticleActionCreator(dispatcher, actionManager, WanMockUtils.wanTestComponent!!.retrofit)
    }

    @Test
    fun getArticleList() {
        //调用接口
        articleActionCreator?.getArticleList(1)
        //验证接口调用成功，发送数据
        verify(dispatcher).postAction(any())
        //验证RxStore接收到数据，因为RxStore是mock的，故该方法并不会通知View更新数据
        verify(articleStore).onGetArticleList(any())
    }

    @Test
    fun getBannerList() {
        //调用接口
        articleActionCreator?.getBannerList()
        //验证RxStore接收到数据，因为RxStore是mock的
        verify(articleStore).onGetBannerLiveData(any())
    }
}