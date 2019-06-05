package com.huyingbao.module.github.ui.login.view

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.huyingbao.module.github.R
import com.huyingbao.test.utils.FragmentScenarioRule
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by liujunfeng on 2019/3/28.
 */
@RunWith(AndroidJUnit4::class)
class LoginFragmentTest {
    @Rule
    var mScenarioRule: FragmentScenarioRule<LoginActivity, LoginFragment> = FragmentScenarioRule(LoginActivity::class.java!!, LoginFragment::class.java!!, null, null)

    @Test
    fun testLogin() {
        onView(withId(R.id.btn_login)).check(matches(withText(R.string.github_label_login)))
    }

    @Test
    fun testCreated() {
        mScenarioRule.getScenario().moveToState(Lifecycle.State.RESUMED)
        mScenarioRule.getScenario().onFragment { fragment -> Assert.assertNotNull(fragment.mActionCreator) }
    }
}