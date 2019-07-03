package com.huyingbao.module.github.ui.star.view

import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.huyingbao.module.github.GithubApplication
import com.huyingbao.module.github.R
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@LargeTest
@RunWith(AndroidJUnit4::class)
@Config(application = GithubApplication::class)
class StarActivityTest {
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(StarActivity::class.java)

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
        activityScenarioRule.scenario.moveToState(Lifecycle.State.RESUMED)
        activityScenarioRule.scenario.onActivity {
            Assert.assertNotNull(it.supportFragmentManager.findFragmentById(R.id.fl_container))
        }
    }
}