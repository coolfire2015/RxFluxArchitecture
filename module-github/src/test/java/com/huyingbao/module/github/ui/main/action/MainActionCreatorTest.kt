package com.huyingbao.module.github.ui.main.action

import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.module.github.module.MockUtils
import com.huyingbao.module.github.module.mockDaggerRule
import com.huyingbao.test.utils.RxJavaRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Spy
import org.mockito.junit.MockitoJUnit

class MainActionCreatorTest {
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

    private var mainActionCreator: MainActionCreator? = null

    @Before
    fun setUp() {
        mainActionCreator = MainActionCreator(rxDispatcher, rxActionManager, MockUtils.component!!.retrofit)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun feedback() {
        mainActionCreator?.feedback("test")
        Mockito.verify(rxDispatcher).postRxAction(Mockito.any())
    }

    @Test
    fun getNewsEvent() {
        mainActionCreator?.getNewsEvent("coolfire2015", 1)
        verify(rxDispatcher).postRxAction(any())
    }

    @Test
    fun getTrendData() {
        mainActionCreator?.getTrendData("Kotlin","monthly")
        verify(rxDispatcher).postRxAction(any())
    }
}