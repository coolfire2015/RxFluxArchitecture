package com.huyingbao.module.wan.ui.login.view;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

/**
 * Created by liujunfeng on 2019/3/28.
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {
    @Test
    public void onRxChanged() {
        Context applicationContext = ApplicationProvider.getApplicationContext();
        // GIVEN
        ActivityScenario<LoginActivity> launch = ActivityScenario.launch(LoginActivity.class);
        // WHEN
        launch.moveToState(Lifecycle.State.CREATED);
        // THEN
        launch.onActivity(activity -> {
        });
    }
}