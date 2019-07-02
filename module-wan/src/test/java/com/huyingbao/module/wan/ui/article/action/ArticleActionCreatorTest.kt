package com.huyingbao.module.wan.ui.article.action

import com.huyingbao.module.wan.module.BaseActionCreatorTest
import com.huyingbao.module.wan.module.MockUtils
import com.huyingbao.module.wan.ui.article.store.ArticleStore
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class ArticleActionCreatorTest : BaseActionCreatorTest() {
    private var articleActionCreator: ArticleActionCreator? = null

    @Mock
    lateinit var articleStore: ArticleStore

    override fun getSubscriberList(): List<Any> {
        return listOfNotNull(articleStore)
    }

    @Before
    fun setUp() {
        articleActionCreator = ArticleActionCreator(rxDispatcher, rxActionManager, MockUtils.component!!.retrofit)
    }

    @Test
    fun getArticleList() {
        articleActionCreator?.getArticleList(1)
        verify(rxDispatcher).postRxAction(any())
        verify(articleStore).setArticleLiveData(any())
    }

    @Test
    fun getBannerList() {
    }
}