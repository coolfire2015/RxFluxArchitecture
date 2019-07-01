package com.huyingbao.module.github.ui.login.action

import android.app.Application
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.module.github.app.GithubAppStore
import com.huyingbao.module.github.module.MockActionCreator
import com.huyingbao.module.github.module.MockUtils
import com.huyingbao.module.github.ui.login.view.LoginActivity
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

/**
 * Created by liujunfeng on 2019/4/3.
 */
class LoginActionCreatorTest : MockActionCreator() {
    private var loginActivity: LoginActivity? = null
    private var loginActionCreator: LoginActionCreator? = null
    private var githubAppStore: GithubAppStore? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        loginActionCreator = LoginActionCreator(rxDispatcher, rxActionManager, MockUtils.component!!.retrofit)
        //mock view
        loginActivity = Mockito.mock(LoginActivity::class.java)
        //mock的view注册订阅
        loginActivity?.let { rxDispatcher.subscribeRxView(it) }
        //spy store
        githubAppStore = Mockito.spy(GithubAppStore(Application(), rxDispatcher))
        //spy的store注册订阅
        githubAppStore?.let { rxDispatcher.subscribeRxStore(it) }
    }

    @After
    fun tearDown() {
        //mock的view取消订阅
        loginActivity?.let { rxDispatcher.unsubscribeRxView(it) }
        //spy的Store取消订阅
        githubAppStore?.let { rxDispatcher.unsubscribeRxStore(it) }
    }

    @Test
    fun login() {
        //调用错误登录方法
        loginActionCreator?.login("asdf", "asdf")
        //验证方法失败，发送RxError
        verify(rxDispatcher).postRxError(any())
        //验证view接收异常
        verify(loginActivity)?.onRxError(any())
    }

    @Test
    fun getLoginUserInfo() {
        loginActionCreator?.getLoginUserInfo()
        //验证方法调用成功，发送RxAction
        verify(rxDispatcher).postRxAction(any())
        //验证store接收RxAction
        verify(githubAppStore)?.onReceiveUserInfo(any())
    }
}