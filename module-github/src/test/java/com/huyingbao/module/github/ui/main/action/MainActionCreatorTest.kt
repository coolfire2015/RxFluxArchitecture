package com.huyingbao.module.github.ui.main.action

import com.huyingbao.module.github.module.BaseActionCreatorTest
import com.huyingbao.module.github.module.MockUtils
import com.huyingbao.module.github.ui.main.store.MainStore
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class MainActionCreatorTest : BaseActionCreatorTest() {
    private var mainActionCreator: MainActionCreator? = null
    private var mainStore: MainStore? = null

    override fun getSubscriberList(): List<Any> {
        mainStore = Mockito.mock(MainStore::class.java)
        return listOfNotNull(mainStore)
    }


    @Before
    fun setUp() {
        mainActionCreator = MainActionCreator(rxDispatcher, rxActionManager, MockUtils.component!!.retrofit)
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
        verify(mainStore)?.onGetNewsEvent(any())
    }

    @Test
    fun getTrendData() {
        mainActionCreator?.getTrendData("Kotlin", "monthly")
        verify(rxDispatcher).postRxAction(any())
    }
}