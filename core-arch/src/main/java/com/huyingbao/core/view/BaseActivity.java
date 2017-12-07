package com.huyingbao.core.view;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.os.Bundle;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public abstract class BaseActivity extends DaggerAppCompatActivity implements BaseView {
    protected Context mContext;
    @Inject
    protected ViewModelProvider.Factory mViewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        afterCreate(savedInstanceState);
    }
}
