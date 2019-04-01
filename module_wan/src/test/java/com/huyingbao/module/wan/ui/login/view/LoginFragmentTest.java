package com.huyingbao.module.wan.ui.login.view;

import android.os.Build;

import com.huyingbao.module.wan.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by liujunfeng on 2019/3/28.
 */
@Config(sdk = Build.VERSION_CODES.P)
@RunWith(AndroidJUnit4.class)
public class LoginFragmentTest {

    @Test
    public void login() {
        FragmentScenario<LoginFragment> launch = FragmentScenario.launchInContainer(LoginFragment.class,
                null,
                R.style.AppTheme,
                null);
        onView(withId(R.id.btn_login)).check(matches(withText(R.string.wan_label_login)));
    }
}