package com.huyingbao.module.wan.ui.article.view

import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.huyingbao.module.wan.R
import com.huyingbao.module.wan.WanApplication
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/**
 * Robolectric配合Espresso使用，实际运行scenario对应的方法。
 * build.gradle配置test src之后，Robolectric使用当前model作为App时的[WanApplication]和AndroidManifest.xml文件。
 *
 * Created by liujunfeng on 2019/3/28.
 */
@RunWith(AndroidJUnit4::class)
@Config(application = WanApplication::class, sdk = [28])
class ArticleActivityTest {
    @get:Rule
    var scenarioRule = ActivityScenarioRule(ArticleActivity::class.java)

    @Before
    fun setUp() {
        //Activity场景移动到Lifecycle.State.CREATED
        scenarioRule.scenario.moveToState(Lifecycle.State.CREATED)
    }

    @After
    fun tearDown() {
        //Activity场景移动到Lifecycle.State.DESTROYED
        scenarioRule.scenario.moveToState(Lifecycle.State.DESTROYED)
    }

    @Test
    fun afterCreate() {
        scenarioRule.scenario.moveToState(Lifecycle.State.RESUMED)
        scenarioRule.scenario.onActivity {
            //验证ArticleListFragment已经添加到当前Activity中
            val fragment = it.supportFragmentManager.findFragmentById(R.id.fl_container)
            Assert.assertTrue(fragment is ArticleListFragment)
        }
    }
}