package com.huyingbao.module.github.ui.main.action

import com.google.common.base.Verify
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.module.github.module.MockUtils
import com.huyingbao.module.github.module.mockDaggerRule
import com.huyingbao.module.github.ui.login.action.LoginActionCreator
import com.huyingbao.test.utils.RxJavaRule
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito
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
    lateinit var mRxDispatcher: RxDispatcher
    @Spy
    lateinit var mRxActionManager: RxActionManager

    private var mMainActionCreator: MainActionCreator? = null

    @Before
    fun setUp() {
        mMainActionCreator = MainActionCreator(mRxDispatcher,mRxActionManager, MockUtils.component!!.retrofit)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testGetLoginUserInfo() {
        mMainActionCreator!!.getLoginUserInfo()
        Mockito.verify(mRxDispatcher).postRxAction(Mockito.any())
    }
}