package com.huyingbao.core.view;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public abstract class RxFluxActivity extends AppCompatActivity implements HasSupportFragmentInjector,RxFluxView {
    @Inject
    protected ViewModelProvider.Factory mViewModelFactory;
    @Inject
    DispatchingAndroidInjector<Fragment> mChildFragmentInjector;

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return mChildFragmentInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }
}
