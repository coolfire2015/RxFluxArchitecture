package com.huyingbao.module.wan.ui.article.view

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.huyingbao.module.wan.R
import com.huyingbao.module.wan.WanApplication
import com.huyingbao.test.utils.FragmentScenarioRule
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(application = WanApplication::class)
class ArticleListFragmentTest {
    @get:Rule
    var scenarioRule: FragmentScenarioRule<ArticleActivity, ArticleListFragment> = FragmentScenarioRule(
            ArticleActivity::class.java,
            ArticleListFragment::class.java,
            null,
            null,
            R.id.fl_container)

    @Before
    fun setUp() {
        scenarioRule.scenario.moveToState(Lifecycle.State.CREATED)
    }

    @After
    fun tearDown() {
        scenarioRule.scenario.moveToState(Lifecycle.State.DESTROYED)
    }

    @Test
    fun getArticleActionCreator() {
        scenarioRule.scenario.onFragment { Assert.assertNotNull(it.articleActionCreator) }
    }

    @Test
    fun getLayoutId() {
        scenarioRule.scenario.onFragment { Assert.assertNotNull(it.getLayoutId()) }
    }

    @Test
    fun afterCreate() {
        scenarioRule.scenario.moveToState(Lifecycle.State.RESUMED)
        scenarioRule.scenario.onFragment {
            Espresso.onView(ViewMatchers.withId(R.id.rv_content))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        }
    }
}