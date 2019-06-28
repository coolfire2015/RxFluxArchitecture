package com.huyingbao.module.wan.ui.article.view

import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.huyingbao.module.wan.WanApplication

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/**
 * Created by liujunfeng on 2019/3/28.
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
@Config(application = WanApplication::class)
class ArticleActivityTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<ArticleActivity> = ActivityScenarioRule(ArticleActivity::class.java)

    @Test
    fun onRxChanged() {
        activityRule.scenario.moveToState(Lifecycle.State.CREATED)
    }
}