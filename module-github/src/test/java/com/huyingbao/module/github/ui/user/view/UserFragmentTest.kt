package com.huyingbao.module.github.ui.user.view

import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.huyingbao.module.github.GithubApplication
import com.huyingbao.module.github.R
import com.huyingbao.test.utils.FragmentScenarioRule
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@LargeTest
@RunWith(AndroidJUnit4::class)
@Config(application = GithubApplication::class)
class UserFragmentTest {
    @get:Rule
    var fragmentScenarioRule = FragmentScenarioRule(
            UserActivity::class.java,
            UserFragment::class.java,
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
    fun getUserActionCreator() {
        fragmentScenarioRule.scenario.onFragment {
            Assert.assertNotNull(it.userActionCreator)
        }
    }

    @Test
    fun getGithubAppStore() {
        fragmentScenarioRule.scenario.onFragment {
            Assert.assertNotNull(it.githubAppStore)
        }
    }

    @Test
    fun getLayoutId() {
    }

    @Test
    fun afterCreate() {
    }
}