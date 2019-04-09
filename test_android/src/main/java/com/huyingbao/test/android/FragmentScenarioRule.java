package com.huyingbao.test.android;

import android.os.Bundle;

import com.huyingbao.test.espresso.R;

import org.junit.rules.ExternalResource;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentFactory;

public final class FragmentScenarioRule<A extends FragmentActivity, F extends Fragment> extends ExternalResource {

    private final FragmentFactory fragmentFactory;
    private final Bundle args;
    private final Class<F> fragmentClass;
    private final Class<A> activityClass;

    private FragmentScenario<A, F> scenario;

    public FragmentScenarioRule(Class<A> activityClass, Class<F> fragmentClass, Bundle args, FragmentFactory fragmentFactory) {
        this.activityClass = activityClass;
        this.fragmentClass = fragmentClass;
        this.args = args;
        this.fragmentFactory = fragmentFactory;
    }

    @Override
    protected void before() throws Throwable {
        scenario = FragmentScenario.launchInContainer(
                activityClass,
                fragmentClass,
                args,
                R.style.FragmentScenarioEmptyFragmentActivityTheme,
                fragmentFactory);
    }

    @Override
    protected void after() {
        scenario.recreate();
    }
}
