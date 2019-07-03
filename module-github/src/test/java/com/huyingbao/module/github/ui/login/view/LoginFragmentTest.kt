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
import com.huyingbao.test.utils.FragmentScenarioRule
import org.hamcrest.core.AllOf
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


/**
 * Created by liujunfeng on 2019/3/28.
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
@Config(application = GithubApplication::class)
class LoginFragmentTest {
    @get:Rule
    var fragmentScenarioRule = FragmentScenarioRule(
            LoginActivity::class.java,
            LoginFragment::class.java,
            null,
            null,
            R.id.fl_container)

    @Before
    fun setUp() {
        fragmentScenarioRule.scenario.moveToState(Lifecycle.State.CREATED)
    }

    @After
    fun tearDown() {
        fragmentScenarioRule.scenario.moveToState(Lifecycle.State.DESTROYED)
    }

    @Test
    fun getLoginActionCreator() {
        fragmentScenarioRule.scenario.onFragment { Assert.assertNotNull(it.loginActionCreator) }
    }

    @Test
    fun getLayoutId() {
        fragmentScenarioRule.scenario.onFragment { Assert.assertNotNull(it.getLayoutId()) }
    }

    @Test
    fun afterCreate() {
        fragmentScenarioRule.scenario.moveToState(Lifecycle.State.RESUMED)
        fragmentScenarioRule.scenario.onFragment {
            Espresso.onView(AllOf.allOf(ViewMatchers.isDisplayed(), ViewMatchers.withId(R.id.btn_login)))
                    .check(ViewAssertions.matches(withText(R.string.github_label_login)))
        }
    }

    @Test
    fun onLogin() {
        fragmentScenarioRule.scenario.moveToState(Lifecycle.State.RESUMED)
        fragmentScenarioRule.scenario.onFragment {
            Espresso.onView(ViewMatchers.withId(R.id.et_username))
                    .perform(ViewActions.clearText(), ViewActions.typeText("asdf"))

            Espresso.onView(ViewMatchers.withId(R.id.et_password))
                    .perform(ViewActions.clearText(), ViewActions.typeText("asf"))

            Espresso.onView(AllOf.allOf(ViewMatchers.isDisplayed(), ViewMatchers.withId(R.id.btn_login)))
                    .perform(ViewActions.click())
        }
    }
}