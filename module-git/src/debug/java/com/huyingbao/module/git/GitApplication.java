package com.huyingbao.module.git;

import com.huyingbao.core.RxFluxApp;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public class GitApplication extends RxFluxApp {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerGitComponent.builder().application(this).build();
    }
}
