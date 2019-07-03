package com.huyingbao.module.github.ui.main.view

import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.huyingbao.module.github.GithubApplication
import com.huyingbao.module.github.R
import com.huyingbao.module.github.ui.login.view.LoginActivity
import com.huyingbao.test.utils.FragmentScenarioRule
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@LargeTest
@RunWith(AndroidJUnit4::class)
@Config(application = GithubApplication::class)
class TrendFragmentTest {
    @get:Rule
    var fragmentScenarioRule = FragmentScenarioRule(
            LoginActivity::class.java,
            TrendFragment::class.java,
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
    fun getMainActionCreator() {
        fragmentScenarioRule.scenario.onFragment { Assert.assertNotNull(it.mainActionCreator) }
    }

    @Test
    fun getLayoutId() {
        fragmentScenarioRule.scenario.onFragment { Assert.assertNotNull(it.targetFragment) }
    }

    @Test
    fun afterCreate() {
    }
}