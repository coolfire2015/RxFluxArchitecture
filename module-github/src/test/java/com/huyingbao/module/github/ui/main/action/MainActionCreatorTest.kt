package com.huyingbao.module.github.ui.main.action

import com.huyingbao.module.github.module.MockActionCreator
import com.huyingbao.module.github.module.MockUtils
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainActionCreatorTest : MockActionCreator() {
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
        verify(rxDispatcher).postRxAction(any())
    }

    @Test
    fun getNewsEvent() {
        mainActionCreator?.getNewsEvent("coolfire2015", 1)
        verify(rxDispatcher).postRxAction(any())
    }

    @Test
    fun getTrendData() {
        mainActionCreator?.getTrendData("Kotlin", "monthly")
        verify(rxDispatcher).postRxAction(any())
    }
}