package com.huyingbao.module.main;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v4.app.Fragment;

import com.huyingbao.core.common.CommonActivity;
import com.huyingbao.module.main.action.MainActionCreator;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;

public abstract class MainModuleActivity extends CommonActivity {
    @Inject
    protected MainActionCreator mActionCreator;
    @Inject
    protected ViewModelProvider.Factory mViewModelFactory;
    @Inject
    DispatchingAndroidInjector<Fragment> mChildFragmentInjector;

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return mChildFragmentInjector;
    }
}
