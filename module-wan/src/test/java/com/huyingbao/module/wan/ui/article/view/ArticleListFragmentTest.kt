package com.huyingbao.module.wan.ui.article.view

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.huyingbao.core.test.fragment.FragmentScenarioRule
import com.huyingbao.module.wan.R
import com.huyingbao.module.wan.WanApplication
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(application = WanApplication::class, sdk = [28])
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
        //验证articleActionCreator已经依赖注入
        scenarioRule.scenario.onFragment { Assert.assertNotNull(it.articleActionCreator) }
    }

    @Test
    fun getLayoutId() {
        //验证布局文件已经设置
        scenarioRule.scenario.onFragment { Assert.assertNotNull(it.getLayoutId()) }
    }

    @Test
    fun afterCreate() {
        scenarioRule.scenario.moveToState(Lifecycle.State.RESUMED)
        scenarioRule.scenario.onFragment {
            //验证Fragment中R.id.rv_content对应的View显示
            Espresso.onView(ViewMatchers.withId(R.id.rv_content))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            it.articleActionCreator.getArticleList(0)
            //验证Fragment持有的Store在接口调用成功之后，更新其维持的LiveData数据
//            Assert.assertTrue(it.store?.articleLiveData?.value?.size!! > 0)
        }
    }
}