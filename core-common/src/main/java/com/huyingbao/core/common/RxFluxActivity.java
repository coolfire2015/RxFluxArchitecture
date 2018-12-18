package com.huyingbao.core.common;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.huyingbao.core.RxFluxView;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public abstract class RxFluxActivity extends AppCompatActivity implements RxFluxView {
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        afterCreate(savedInstanceState);
    }
}
