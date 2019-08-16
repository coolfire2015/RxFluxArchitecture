package com.huyingbao.module.first.ui.action

import com.huyingbao.module.first.module.BaseSubscriberTest
import com.huyingbao.module.first.module.MockUtils
import com.huyingbao.module.first.ui.store.FirstStore
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class FirstActionCreatorTest : BaseSubscriberTest() {
    private var articleStore: FirstStore? = null

    private var articleActionCreator: FirstActionCreator? = null

    override fun getSubscriberList(): List<Any> {
        articleStore = Mockito.mock(FirstStore::class.java)
        return listOfNotNull(articleStore)
    }

    @Before
    fun setUp() {
        articleActionCreator = FirstActionCreator(rxDispatcher, rxActionManager, MockUtils.component!!.retrofit)
    }

    @Test
    fun getHotKey() {
        //调用接口
        articleActionCreator?.getHotKey()
        //验证接口调用成功，发送数据
        verify(rxDispatcher).postRxAction(any())
        //验证RxStore接收到数据，因为RxStore是mock的，故该方法并不会通知View更新数据
        verify(articleStore)?.onGetHotKey(any())
    }
}