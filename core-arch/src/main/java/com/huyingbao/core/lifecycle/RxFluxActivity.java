package com.huyingbao.core.lifecycle;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.huyingbao.core.RxFluxView;

import butterknife.ButterKnife;
import dagger.android.DispatchingAndroidInjector;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public abstract class RxFluxActivity extends AppCompatActivity implements RxFluxView{
    protected Context mContext;
    DispatchingAndroidInjector<Fragment> mSupportFragmentInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        afterCreate(savedInstanceState);
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return mSupportFragmentInjector;
    }
}
