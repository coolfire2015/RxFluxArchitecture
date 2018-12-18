package com.huyingbao.core.common;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.huyingbao.core.RxFluxView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public abstract class RxFluxActivity extends AppCompatActivity implements RxFluxView {
    @Inject
    protected ViewModelProvider.Factory mViewModelFactory;
    @Inject
    DispatchingAndroidInjector<Fragment> mChildFragmentInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        afterCreate(savedInstanceState);
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return mChildFragmentInjector;
    }
}
