package com.huyingbao.core.common;


import android.content.Context;

import com.huyingbao.core.arch.BuildConfig;
import com.huyingbao.core.arch.RxFluxApp;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import androidx.annotation.CallSuper;
import androidx.multidex.MultiDex;

/**
 * Application实现相应的接口
 * HasActivityInjector、
 * HasFragmentInjector、
 * HasSupportFragmentInjector、
 * HasServiceInjector、
 * HasBroadcastReceiverInjector
 * Created by liujunfeng on 2017/12/7.
 */
public abstract class CommonApp extends RxFluxApp {
    @Override
    @CallSuper//强制子类复写该方法时调用父方法
    public void onCreate() {
        super.onCreate();
        initDebug();
    }

    /**
     * multidex分包
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 初始化debug工具
     */
    private void initDebug() {
        //.logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
        //.methodOffset(5)        // (Optional) Hides internal method calls up to offset. Default 5
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
                .tag("RxFlux")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
        //TODO 使用Lazy加载Fragment导致内存泄露
//        if (BuildConfig.DEBUG) {
//            if (LeakCanary.isInAnalyzerProcess(this)) return;
//            LeakCanary.install(this);
//        }
    }
}
