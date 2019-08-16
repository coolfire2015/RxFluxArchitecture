package com.huyingbao.core.base

import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.huyingbao.core.arch.RxApp

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
}
