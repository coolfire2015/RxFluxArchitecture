package com.huyingbao.module.git;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v4.app.Fragment;

import com.huyingbao.core.common.CommonFragment;
import com.huyingbao.module.git.action.GitActionCreator;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;

public abstract class GitModuleFragment extends CommonFragment {
    @Inject
    protected GitActionCreator mActionCreator;
    @Inject
    protected ViewModelProvider.Factory mViewModelFactory;
    @Inject
    DispatchingAndroidInjector<Fragment> mChildFragmentInjector;

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return mChildFragmentInjector;
    }
}
