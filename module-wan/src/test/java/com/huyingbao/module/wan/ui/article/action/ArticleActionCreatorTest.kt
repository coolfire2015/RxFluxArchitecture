package com.huyingbao.module.wan.ui.article.action

import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.module.wan.module.MockUtils
import com.huyingbao.module.wan.module.mockDaggerRule
import com.huyingbao.test.utils.RxJavaRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Spy
import org.mockito.junit.MockitoJUnit

class ArticleActionCreatorTest {
    @get:Rule
    var rxJavaRule = RxJavaRule()
    @get:Rule
    var mockitoRule = MockitoJUnit.rule()
    @get:Rule
    var mockDaggerRule = mockDaggerRule()

    @Spy
    lateinit var rxDispatcher: RxDispatcher
    @Spy
    lateinit var rxActionManager: RxActionManager

    private var articleActionCreator: ArticleActionCreator? = null

    @Before
    fun setUp() {
        articleActionCreator = ArticleActionCreator(rxDispatcher, rxActionManager, MockUtils.component!!.retrofit)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getArticleList() {
        articleActionCreator?.getArticleList(1)
        verify(rxDispatcher).postRxAction(any())
    }

    @Test
    fun getBannerList() {
    }
}