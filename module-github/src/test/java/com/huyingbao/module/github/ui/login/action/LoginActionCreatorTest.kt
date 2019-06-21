package com.huyingbao.module.github.ui.login.action

import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.module.github.module.MockUtils
import com.huyingbao.module.github.module.mockDaggerRule
import com.huyingbao.test.utils.RxJavaRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.verify
import org.mockito.Spy
import org.mockito.junit.MockitoJUnit

/**
 * Created by liujunfeng on 2019/4/3.
 */
class LoginActionCreatorTest {
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

    private var mLoginActionCreator: LoginActionCreator? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        mLoginActionCreator = LoginActionCreator(mRxDispatcher, mRxActionManager, MockUtils.component!!.retrofit)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun login() {
        mLoginActionCreator!!.login("asdf", "asdf")
        //验证方法正确执行,并发送RxAction
        verify(mRxDispatcher).postRxAction(any<RxAction>())
    }

    @Test
    fun getLoginUserInfo() {
        mLoginActionCreator!!.getLoginUserInfo()
        verify(mRxDispatcher).postRxAction(any<RxAction>())
    }
}