package com.huyingbao.core.base;


import android.content.Context;

import androidx.annotation.CallSuper;
import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.huyingbao.core.arch.RxApp;
import com.huyingbao.core.common.BuildConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class BaseApp extends RxApp {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex分包
        MultiDex.install(this);
    }

    /**
     * 注解{@link CallSuper}强制子类复写该方法时调用父方法
     */
    @Override
    @CallSuper
    public void onCreate() {
        super.onCreate();
        initARouter();
        initDebug();
    }

    /**
     * 初始化ARouter
     */
    private void initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(this);
    }

    /**
     * 初始化debug工具
     */
    private void initDebug() {
        //.logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
        //.methodOffset(5)        // (Optional) Hides internal method calls up to offset. Default 5
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                // (Optional) Whether to show thread info or not. Default true
                .showThreadInfo(false)
                // (Optional) How many method line to show. Default 2
                .methodCount(2)
                // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .tag("RxFlux")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }
}
