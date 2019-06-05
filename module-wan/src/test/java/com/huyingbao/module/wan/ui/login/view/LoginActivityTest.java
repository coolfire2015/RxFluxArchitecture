package com.huyingbao.module.wan.ui.login.view;

import androidx.lifecycle.Lifecycle;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by liujunfeng on 2019/3/28.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {
    @Rule
    public ActivityScenarioRule<LoginActivity> mActivityRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void testOnRxChanged() {
        mActivityRule.getScenario().moveToState(Lifecycle.State.CREATED);
    }
}