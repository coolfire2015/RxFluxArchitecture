package com.huyingbao.module.github.ui.login.action

import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.module.github.module.MockUtils
import com.huyingbao.module.github.module.mockDaggerRule
import com.huyingbao.module.github.ui.login.view.LoginActivity
import com.huyingbao.test.utils.RxJavaRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
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
    lateinit var rxDispatcher: RxDispatcher

    @Spy
    lateinit var rxActionManager: RxActionManager

    private var loginActivity: LoginActivity? = null
    private var loginActionCreator: LoginActionCreator? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        loginActionCreator = LoginActionCreator(rxDispatcher, rxActionManager, MockUtils.component!!.retrofit)
        loginActivity = Mockito.mock(LoginActivity::class.java)
        loginActivity?.let { rxDispatcher.subscribeRxView(it) }
    }

    @After
    fun tearDown() {
    }

    @Test
    fun login() {
        loginActionCreator?.login("asdf", "asdf")
        //验证方法正确执行,并发送RxAction
        verify(rxDispatcher).postRxAction(any())
    }

    @Test
    fun getLoginUserInfo() {
        loginActionCreator?.getLoginUserInfo()
        verify(loginActivity)?.onRxError(any())
//        verify(rxDispatcher).postRxAction(any())
    }
}