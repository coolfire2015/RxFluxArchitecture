package com.huyingbao.core.base


import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.huyingbao.core.arch.RxApp
import com.huyingbao.core.common.BuildConfig
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

/**
 * 全局通用App
 *
 * Created by liujunfeng on 2019/1/1.
 */
abstract class BaseApp : RxApp() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        //MultiDex分包
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        initARouter()
        initDebug()
    }

    /**
     * 初始化ARouter
     */
    private fun initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(this)
    }

    /**
     * 初始化debug工具
     */
    private fun initDebug() {
        //.logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
        //.methodOffset(5)        // (Optional) Hides internal method calls up to offset. Default 5
        val formatStrategy = PrettyFormatStrategy.newBuilder()
                // (Optional) Whether to show thread info or not. Default true
                .showThreadInfo(false)
                // (Optional) How many method line to show. Default 2
                .methodCount(2)
                // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .tag("RxFlux")
                .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }
}
