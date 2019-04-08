package com.huyingbao.test;

import android.os.Bundle;

import com.huyingbao.test.espresso.R;

import org.junit.rules.ExternalResource;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;

public final class FragmentScenarioRule<F extends Fragment> extends ExternalResource {

    private final FragmentFactory fragmentFactory;
    private final Bundle args;
    private final Class<Fragment> clazz;

    private FragmentScenario<Fragment> fragmentScenario;

    public FragmentScenarioRule(FragmentFactory fragmentFactory, Bundle args, Class<Fragment> clazz) {
        this.fragmentFactory = fragmentFactory;
        this.args = args;
        this.clazz = clazz;

    }

    @Override
    protected void before() throws Throwable {
        super.before();
        fragmentScenario = FragmentScenario.launchInContainer(clazz, args, R.style.FragmentScenarioEmptyFragmentActivityTheme, fragmentFactory);
    }

    @Override
    protected void after() {
        super.after();
        fragmentScenario.recreate();
    }
}
