package com.huyingbao.core.base


import android.content.Context
import android.os.Build
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.huyingbao.core.arch.RxApp
import com.huyingbao.core.common.BuildConfig
import com.huyingbao.core.common.utils.AndroidUtils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.bugly.crashreport.CrashReport.UserStrategy
import com.tencent.smtt.sdk.QbSdk


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
        initBugly()
        initDebug()
        initARouter()
        initX5()
    }

    /**
     * 初始化Bugly
     */
    private fun initBugly() {
        //配置策略
        val strategy = UserStrategy(this)
        // 获取当前进程名
        val processName = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getProcessName()
        } else {
            AndroidUtils.getProcessName(android.os.Process.myPid())
        }
        // 设置是否为上报进程
        strategy.isUploadProcess = processName == null || processName == packageName
        // X5内核异常上报
        strategy.setCrashHandleCallback(object : CrashReport.CrashHandleCallback() {
            override fun onCrashHandleStart(p0: Int, p1: String?, p2: String?, p3: String?): MutableMap<String, String> {
                val map = LinkedHashMap<String, String>()
                val x5CrashInfo = com.tencent.smtt.sdk.WebView.getCrashExtraMessage(this@BaseApp)
                map["x5crashInfo"] = x5CrashInfo
                return map
            }

            override fun onCrashHandleStart2GetExtraDatas(p0: Int, p1: String?, p2: String?, p3: String?): ByteArray? {
                return try {
                    "Extra data.".toByteArray(charset("UTF-8"))
                } catch (e: Exception) {
                    null
                }
            }
        })
        //设置开发设备
        CrashReport.setIsDevelopmentDevice(this, BuildConfig.DEBUG)
        //初始化Bugly
        CrashReport.initCrashReport(this, "6da8b7224c", BuildConfig.DEBUG, strategy)
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
     * 初始化X5内核
     */
    private fun initX5() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        val cb = object : QbSdk.PreInitCallback {
            override fun onViewInitFinished(arg0: Boolean) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Logger.d("onViewInitFinished is $arg0")
            }

            override fun onCoreInitFinished() {
                Logger.d("onCoreInitFinished")
            }
        }
        //x5内核初始化接口
        QbSdk.initX5Environment(this, cb)
    }
}
