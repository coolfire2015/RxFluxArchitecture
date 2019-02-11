package com.huyingbao.core.common


import android.content.Context

import com.alibaba.android.arouter.launcher.ARouter
import com.huyingbao.core.arch.BuildConfig
import com.huyingbao.core.arch.RxFluxApp
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

import androidx.annotation.CallSuper
import androidx.multidex.MultiDex

/**
 * Application实现相应的接口
 * HasActivityInjector、
 * HasFragmentInjector、
 * HasSupportFragmentInjector、
 * HasServiceInjector、
 * HasBroadcastReceiverInjector
 * Created by liujunfeng on 2019/1/1.
 */
abstract class CommonApp : RxFluxApp() {
    @CallSuper//强制子类复写该方法时调用父方法
    override fun onCreate() {
        super.onCreate()
        initArouter()
        initDebug()
    }

    /**
     * multidex分包
     *
     * @param base
     */
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    /**
     * 初始化Arouter
     */
    private fun initArouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
    }

    /**
     * 初始化debug工具
     */
    private fun initDebug() {
        //.logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
        //.methodOffset(5)        // (Optional) Hides internal method calls up to offset. Default 5
        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
                .tag("RxFlux")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }
}
