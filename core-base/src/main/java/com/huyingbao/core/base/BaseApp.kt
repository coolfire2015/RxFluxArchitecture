package com.huyingbao.core.base

import android.content.Context
import androidx.multidex.MultiDex
import com.huyingbao.core.arch.FluxApp

/**
 * 全局通用App
 *
 * Created by liujunfeng on 2019/1/1.
 */
abstract class BaseApp : FluxApp() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        //MultiDex分包
        MultiDex.install(this)
    }
}
