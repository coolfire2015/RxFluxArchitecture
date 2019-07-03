package com.huyingbao.module.github.ui.main.view

import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.huyingbao.module.github.GithubApplication
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@LargeTest
@RunWith(AndroidJUnit4::class)
@Config(application = GithubApplication::class)
class MainActivityTest {
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        activityScenarioRule.scenario.moveToState(Lifecycle.State.CREATED)
    }

    @After
    fun tearDown() {
        activityScenarioRule.scenario.moveToState(Lifecycle.State.DESTROYED)
    }

    @Test
    fun getGithubAppStore() {
        activityScenarioRule.scenario.onActivity {
            Assert.assertNotNull(it.githubAppStore)
        }
    }

    @Test
    fun getMainActionCreator() {
        activityScenarioRule.scenario.onActivity {
            Assert.assertNotNull(it.mainActionCreator)
        }
    }

    @Test
    fun getLayoutId() {
        activityScenarioRule.scenario.onActivity {
            Assert.assertNotNull(it.getLayoutId())
        }
    }

    @Test
    fun afterCreate() {
        activityScenarioRule.scenario.onActivity {  }
    }

    @Test
    fun onBackPressed() {
    }
}