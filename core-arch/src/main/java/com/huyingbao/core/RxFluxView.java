package com.huyingbao.core;

import android.os.Bundle;

import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public interface RxFluxView extends HasSupportFragmentInjector {
    int getLayoutId();

    void afterCreate(Bundle savedInstanceState);
}
