package com.huyingbao.core.arch;


import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerApplication;

/**
 * Application实现相应的接口
 * HasActivityInjector、
 * HasFragmentInjector、
 * HasSupportFragmentInjector、
 * HasServiceInjector、
 * HasBroadcastReceiverInjector
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class RxFluxApp extends DaggerApplication {
    @Inject
    RxFlux mRxFlux;

    private List<RxAppLifecycle> mRxAppLifecycles;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        for (RxAppLifecycle appLifecycles : mRxAppLifecycles) {
//            appLifecycles.attachBaseContext(base);
//        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //application创建的时候调用该方法，
        //使RxFlux可以接受Activity生命周期回调
        registerActivityLifecycleCallbacks(mRxFlux);
//        for (RxAppLifecycle appLifecycles : mRxAppLifecycles) {
//            appLifecycles.onCreate(this);
//        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
//        for (RxAppLifecycle appLifecycles : mRxAppLifecycles) {
//            appLifecycles.onTerminate(this);
//        }
    }
}
