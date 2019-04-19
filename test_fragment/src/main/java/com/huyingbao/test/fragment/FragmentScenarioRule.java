package com.huyingbao.test.fragment;

import android.os.Bundle;

import org.junit.rules.ExternalResource;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentFactory;
import androidx.test.core.app.ActivityScenario;

import static androidx.test.internal.util.Checks.checkNotNull;

/**
 * Created by liujunfeng on 2019/4/3.
 *
 * @param <A> 容纳Fragment的Activity
 * @param <F> 需要测试的Fragment
 */
public final class FragmentScenarioRule<A extends FragmentActivity, F extends Fragment> extends ExternalResource {
    /**
     * Same as {@link java.util.function.Supplier} which requires API level 24.
     *
     * @hide
     */
    interface Supplier<T> {
        T get();
    }

    private final FragmentScenarioRule.Supplier<FragmentScenario<A, F>> scenarioSupplier;

    @Nullable
    private FragmentScenario<A, F> scenario;

    public FragmentScenarioRule(
            Class<A> activityClass,
            Class<F> fragmentClass,
            Bundle args,
            FragmentFactory fragmentFactory) {
        scenarioSupplier = () -> FragmentScenario.launchInContainer(
                checkNotNull(activityClass),
                checkNotNull(fragmentClass),
                args,
                R.style.FragmentScenarioEmptyFragmentActivityTheme,
                fragmentFactory);
    }


    @Override
    protected void before() throws Throwable {
        scenario = scenarioSupplier.get();
    }

    @Override
    protected void after() {
        scenario.recreate();
    }

    /**
     * Returns {@link ActivityScenario} of the given activity class.
     *
     * @return a non-null {@link ActivityScenario} instance
     * @throws NullPointerException if you call this method while test is not running
     */
    public FragmentScenario<A, F> getScenario() {
        return checkNotNull(scenario);
    }
}
