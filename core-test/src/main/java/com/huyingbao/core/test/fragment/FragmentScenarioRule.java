package com.huyingbao.core.test.fragment;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentFactory;
import androidx.test.core.app.ActivityScenario;
import androidx.test.internal.util.Checks;

import com.huyingbao.core.test.R;

import org.junit.rules.ExternalResource;

/**
 * Created by liujunfeng on 2019/4/3.
 *
 * @param <A> 容纳Fragment的Activity
 * @param <F> 需要测试的Fragment
 */
public final class FragmentScenarioRule<A extends FragmentActivity, F extends Fragment> extends ExternalResource {

    private final Supplier<FragmentScenario<A, F>> scenarioSupplier;
    @Nullable
    private FragmentScenario<A, F> scenario;

    /**
     * Same as {@link java.util.function.Supplier} which requires API level 24.
     *
     * @hide
     */
    interface Supplier<T> {
        T get();
    }

    /**
     * Fragment 添加到 Activity 通用布局ID上
     */
    public FragmentScenarioRule(
            @NonNull final Class<A> activityClass,
            @NonNull final Class<F> fragmentClass,
            @Nullable final Bundle fragmentArgs,
            @Nullable final FragmentFactory factory) {
        this(activityClass, fragmentClass, fragmentArgs, factory, android.R.id.content);
    }

    /**
     * Fragment 添加到 Activity 的布局ID上
     */
    public FragmentScenarioRule(
            @NonNull final Class<A> activityClass,
            @NonNull final Class<F> fragmentClass,
            @Nullable final Bundle fragmentArgs,
            @Nullable final FragmentFactory factory,
            @IdRes final int containerViewId) {
        scenarioSupplier = () -> FragmentScenario.internalLaunch(
                Checks.checkNotNull(activityClass),
                Checks.checkNotNull(fragmentClass),
                fragmentArgs,
                R.style.FragmentScenarioEmptyFragmentActivityTheme,
                factory,
                containerViewId);
    }

    @Override
    protected void before() throws Throwable {
        scenario = scenarioSupplier.get();
    }

    @Override
    protected void after() {
        scenario.mActivityScenario.close();
    }

    /**
     * Returns {@link FragmentScenario} of the given activity class.
     *
     * @return a non-null {@link ActivityScenario} instance
     * @throws NullPointerException if you call this method while test is not running
     */
    public FragmentScenario<A, F> getScenario() {
        return Checks.checkNotNull(scenario);
    }
}
