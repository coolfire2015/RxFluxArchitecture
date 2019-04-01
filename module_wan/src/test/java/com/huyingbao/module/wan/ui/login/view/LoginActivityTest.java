package com.huyingbao.module.wan.ui.login.view;

import android.content.Context;

import com.huyingbao.module.wan.FakeWanApplication;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

/**
 * Created by liujunfeng on 2019/3/28.
 */
@Config(application = FakeWanApplication.class)
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void onRxChanged() {
        Context applicationContext = ApplicationProvider.getApplicationContext();
        // GIVEN
        ActivityScenario<LoginActivity> launch = ActivityScenario.launch(LoginActivity.class);
        // WHEN
        launch.moveToState(Lifecycle.State.CREATED);
        // THEN
        launch.onActivity(activity -> Assert.assertNotNull(activity.mLoginFragmentLazy));
    }
}