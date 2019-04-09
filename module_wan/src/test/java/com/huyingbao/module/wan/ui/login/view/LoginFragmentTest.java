package com.huyingbao.module.wan.ui.login.view;

import com.huyingbao.module.wan.R;
import com.huyingbao.test.android.FragmentScenarioRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by liujunfeng on 2019/3/28.
 */
@RunWith(AndroidJUnit4.class)
public class LoginFragmentTest {
    @Rule
    public FragmentScenarioRule<LoginActivity, LoginFragment> mScenarioRule = new FragmentScenarioRule<>(LoginActivity.class, LoginFragment.class, null, null);

    @Test
    public void login() {
        onView(withId(R.id.btn_login)).check(matches(withText(R.string.wan_label_login)));
    }
}