package com.huyingbao.core.arch;

import android.app.Application;

import androidx.lifecycle.LifecycleObserver;

/**
 * 跟随App生命周期
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public class RxAppLifecycle implements LifecycleObserver {

    protected Application mApplication;

    public RxAppLifecycle(Application application) {
        mApplication = application;
    }
}
