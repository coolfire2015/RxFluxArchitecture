package com.huyingbao.core.arch;


import com.alibaba.android.arouter.launcher.ARouter;

import javax.inject.Inject;

import dagger.android.support.DaggerApplication;

/**
 * Application实现相应的接口
 * HasActivityInjector、
 * HasFragmentInjector、
 * HasSupportFragmentInjector、
 * HasServiceInjector、
 * HasBroadcastReceiverInjector
 * Created by liujunfeng on 2017/12/7.
 */
public abstract class RxFluxApp extends DaggerApplication {
    @Inject
    RxFlux mRxFlux;

    @Override
    public void onCreate() {
        super.onCreate();
        //application创建的时候调用该方法，
        //使RxFlux可以接受Activity生命周期回调
        registerActivityLifecycleCallbacks(mRxFlux);
        initArouter();
    }

    /**
     * 初始化Arouter
     */
    private void initArouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }
}
