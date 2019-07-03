package com.huyingbao.module.github.ui.user.view

import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.huyingbao.module.github.GithubApplication
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@LargeTest
@RunWith(AndroidJUnit4::class)
@Config(application = GithubApplication::class)
class UserActivityTest {
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(UserActivity::class.java)

    @Before
    fun setUp() {
        activityScenarioRule.scenario.moveToState(Lifecycle.State.CREATED)
    }

    @After
    fun tearDown() {
        activityScenarioRule.scenario.moveToState(Lifecycle.State.DESTROYED)
    }

    @Test
    fun afterCreate() {
        activityScenarioRule.scenario.moveToState(Lifecycle.State.CREATED)
//        activityScenarioRule.scenario.onActivity {
//            Assert.assertNotNull(it.supportFragmentManager.findFragmentById(R.id.fl_container))
//        }
    }
}