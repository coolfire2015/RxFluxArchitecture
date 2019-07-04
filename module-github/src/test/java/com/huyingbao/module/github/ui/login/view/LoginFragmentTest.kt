package com.huyingbao.module.github.ui.login.view

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.huyingbao.module.github.GithubApplication
import com.huyingbao.module.github.R
import com.huyingbao.module.github.module.BaseSubscriberTest
import com.huyingbao.module.github.ui.login.store.LoginStore
import com.huyingbao.test.utils.FragmentScenarioRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import org.hamcrest.core.AllOf
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.annotation.Config


/**
 * Created by liujunfeng on 2019/3/28.
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
@Config(application = GithubApplication::class)
class LoginFragmentTest : BaseSubscriberTest() {
    @get:Rule
    var scenarioRule = FragmentScenarioRule(
            LoginActivity::class.java,
            LoginFragment::class.java,
            null,
            null,
            R.id.fl_container)

    private var loginStore: LoginStore? = null
    private var loginActivity: LoginActivity? = null

    override fun getSubscriberList(): List<Any> {
        loginStore = Mockito.mock(LoginStore::class.java)
        loginActivity = Mockito.mock(LoginActivity::class.java)
        return listOfNotNull(loginStore, loginActivity)
    }

    @Before
    fun setUp() {
        scenarioRule.scenario.moveToState(Lifecycle.State.CREATED)
    }

    @After
    fun tearDown() {
        scenarioRule.scenario.moveToState(Lifecycle.State.DESTROYED)
    }

    @Test
    fun getLoginActionCreator() {
        scenarioRule.scenario.onFragment { Assert.assertNotNull(it.loginActionCreator) }
    }

    @Test
    fun getLayoutId() {
        scenarioRule.scenario.onFragment { Assert.assertNotNull(it.getLayoutId()) }
    }

    @Test
    fun afterCreate() {
        scenarioRule.scenario.moveToState(Lifecycle.State.RESUMED)
        scenarioRule.scenario.onFragment {
            Espresso.onView(AllOf.allOf(ViewMatchers.isDisplayed(), ViewMatchers.withId(R.id.btn_login)))
                    .check(ViewAssertions.matches(withText(R.string.github_label_login)))
        }
    }

    @Test
    fun onLogin() {
        scenarioRule.scenario.moveToState(Lifecycle.State.RESUMED)
        scenarioRule.scenario.onFragment {
            //输入用户名
            Espresso.onView(ViewMatchers.withId(R.id.et_username))
                    .perform(ViewActions.clearText(), ViewActions.typeText("coolfire2015"))
            //输入错误密码
            Espresso.onView(ViewMatchers.withId(R.id.et_password))
                    .perform(ViewActions.clearText(), ViewActions.typeText("asf"))
            //调用登录方法
            Espresso.onView(AllOf.allOf(ViewMatchers.isDisplayed(), ViewMatchers.withId(R.id.btn_login)))
                    .perform(ViewActions.click())
            //验证登录失败，Activity中统一错误处理方法得到调用
            verify(loginActivity)?.onRxError(any())
        }
    }
}