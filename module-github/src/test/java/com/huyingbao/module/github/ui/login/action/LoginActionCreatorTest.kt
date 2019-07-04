package com.huyingbao.module.github.ui.login.action

import com.huyingbao.module.github.BuildConfig
import com.huyingbao.module.github.app.GithubAppStore
import com.huyingbao.module.github.module.BaseSubscriberTest
import com.huyingbao.module.github.module.MockUtils
import com.huyingbao.module.github.ui.login.store.LoginStore
import com.huyingbao.module.github.ui.login.view.LoginActivity
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.Mockito

/**
 * Created by liujunfeng on 2019/4/3.
 */
class LoginActionCreatorTest : BaseSubscriberTest() {
    private var loginStore: LoginStore? = null
    private var githubAppStore: GithubAppStore? = null
    private var loginActivity: LoginActivity? = null
    private var loginActionCreator: LoginActionCreator? = null

    override fun getSubscriberList(): List<Any> {
        loginStore = Mockito.mock(LoginStore::class.java)
        githubAppStore = Mockito.mock(GithubAppStore::class.java)
        loginActivity = Mockito.mock(LoginActivity::class.java)
        return listOfNotNull(loginStore, githubAppStore, loginActivity)
    }

    @Before
    @Throws(Exception::class)
    fun setUp() {
        loginActionCreator = LoginActionCreator(rxDispatcher, rxActionManager, MockUtils.component!!.retrofit)
    }

    @Ignore("此方法会修改Auth Token，需先获取Log中的Token，放到MockModule中")
    @Test
    fun login() {
        //调用错误登录方法
        loginActionCreator?.login("asdf", "pwd")
        //验证方法失败，发送RxError
        verify(rxDispatcher).postRxError(any())
        //验证view接收异常
        verify(loginActivity)?.onRxError(any())
        //调用错误登录方法
        loginActionCreator?.login(BuildConfig.GIT_NAME, BuildConfig.GIT_PWD)
        //验证方法调用成功，发送RxAction
        verify(rxDispatcher).postRxAction(any())
        //验证store接收RxAction
        verify(loginStore)?.onLogin(any())
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