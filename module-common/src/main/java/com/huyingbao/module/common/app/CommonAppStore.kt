//package com.huyingbao.module.common.app
//
//import android.app.Application
//import android.content.Context
//import androidx.lifecycle.Lifecycle
//import androidx.lifecycle.LifecycleOwner
//import androidx.lifecycle.OnLifecycleEvent
//import com.alibaba.android.arouter.launcher.ARouter
//import com.huyingbao.core.annotations.AppObserver
//import com.huyingbao.core.arch.store.FluxAppStore
//import com.huyingbao.core.utils.AndroidUtils
//import com.huyingbao.module.common.BuildConfig
//import com.orhanobut.logger.AndroidLogAdapter
//import com.orhanobut.logger.Logger
//import com.orhanobut.logger.PrettyFormatStrategy
//import com.tencent.bugly.crashreport.CrashReport
//import com.tencent.smtt.sdk.QbSdk
//import dagger.hilt.android.qualifiers.ApplicationContext
//import javax.inject.Inject
//import javax.inject.Singleton
//
///**
// * Application生命周期方法分发类
// *
// * Created by liujunfeng on 2019/8/1.
// */
//@Singleton
//@AppObserver
//class CommonAppStore @Inject constructor(
//        @ApplicationContext private val context: Context
//) : FluxAppStore(context as Application) {
//    override fun onCreate(owner: LifecycleOwner) {
//        super.onCreate(owner)
//        initARouter()
//        initBugly()
//        initDebug()
//        initX5()
//    }
//
//    /**
//     * 初始化ARouter
//     */
//    private fun initARouter() {
//        if (BuildConfig.DEBUG) {
//            ARouter.openLog()
//            ARouter.openDebug()
//        }
//        // 尽可能早，推荐在Application中初始化
//        ARouter.init(context as Application?)
//    }
//
//    /**
//     * 初始化Bugly
//     */
//    private fun initBugly() {
//        //配置策略
//        val strategy = CrashReport.UserStrategy(context)
//        // 获取当前进程名
//        val processName = AndroidUtils.getProcessName(android.os.Process.myPid())
//        // 设置是否为上报进程
//        strategy.isUploadProcess = processName == null || processName == context.packageName
//        // X5内核异常上报
//        strategy.setCrashHandleCallback(object : CrashReport.CrashHandleCallback() {
//            override fun onCrashHandleStart(
//                p0: Int,
//                p1: String?,
//                p2: String?,
//                p3: String?
//            ): MutableMap<String, String> {
//                val map = LinkedHashMap<String, String>()
//                val x5CrashInfo = com.tencent.smtt.sdk.WebView.getCrashExtraMessage(context)
//                map["x5crashInfo"] = x5CrashInfo
//                return map
//            }
//
//            override fun onCrashHandleStart2GetExtraDatas(
//                p0: Int,
//                p1: String?,
//                p2: String?,
//                p3: String?
//            ): ByteArray? {
//                return try {
//                    "Extra data.".toByteArray(charset("UTF-8"))
//                } catch (e: Exception) {
//                    null
//                }
//            }
//        })
//        //设置开发设备
//        CrashReport.setIsDevelopmentDevice(context, BuildConfig.DEBUG)
//        //初始化Bugly
//        CrashReport.initCrashReport(context, "6da8b7224c", BuildConfig.DEBUG, strategy)
//    }
//
////    /**
////     * 我们需要确保至少对主进程跟patch进程初始化 TinkerPatch
////     */
////    private fun initTinker() {
////        // 我们可以从这里获得Tinker加载过程的信息
////        tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike()
////        if (tinkerApplicationLike == null) {
////            Logger.e("获取Tinker加载过程信息失败！")
////            return
////        }
////        // 初始化TinkerPatch SDK
////        // new TinkerPatch.Builder(tinkerApplicationLike).requestLoader(new OkHttp3Loader()).build()
////        TinkerPatch.init(tinkerApplicationLike)
////            .reflectPatchLibrary()
////            .setPatchRollbackOnScreenOff(true)
////            .setPatchRestartOnSrceenOff(true)
////            .setFetchPatchIntervalByHours(3)
////        // 获取当前的补丁版本
////        Logger.d("Current patch version is " + TinkerPatch.with().patchVersion!!)
////
////        // fetchPatchUpdateAndPollWithInterval 与 fetchPatchUpdate(false)
////        // 不同的是，会通过handler的方式去轮询
////        TinkerPatch.with().fetchPatchUpdateAndPollWithInterval()
////    }
////
////    /**
////     * 在这里给出TinkerPatch的所有接口解释
////     * 更详细的解释请参考:http://tinkerpatch.com/Docs/api
////     */
////    private fun useSample() {
////        TinkerPatch.init(tinkerApplicationLike)
////            //是否自动反射Library路径,无须手动加载补丁中的So文件
////            //注意,调用在反射接口之后才能生效,你也可以使用Tinker的方式加载Library
////            .reflectPatchLibrary()
////            //向后台获取是否有补丁包更新,默认的访问间隔为3个小时
////            //若参数为true,即每次调用都会真正的访问后台配置
////            .fetchPatchUpdate(false)
////            //设置访问后台补丁包更新配置的时间间隔,默认为3个小时
////            .setFetchPatchIntervalByHours(3)
////            //向后台获得动态配置,默认的访问间隔为3个小时
////            //若参数为true,即每次调用都会真正的访问后台配置
////            .fetchDynamicConfig(
////                object : ConfigRequestCallback {
////                    override fun onSuccess(hashMap: HashMap<String, String>) {}
////                    override fun onFail(e: Exception) {}
////                },
////                false
////            )
////            //设置访问后台动态配置的时间间隔,默认为3个小时
////            .setFetchDynamicConfigIntervalByHours(3)
////            //设置当前渠道号,对于某些渠道我们可能会想屏蔽补丁功能
////            //设置渠道后,我们就可以使用后台的条件控制渠道更新
////            .setAppChannel("default")
////            //屏蔽部分渠道的补丁功能
////            .addIgnoreAppChannel("googleplay")
////            //设置tinkerpatch平台的条件下发参数
////            .setPatchCondition("test", "1")
////            //设置补丁合成成功后,锁屏重启程序
////            //默认是等应用自然重启
////            .setPatchRestartOnSrceenOff(true)
////            //我们可以通过ResultCallBack设置对合成后的回调
////            //例如弹框什么
////            //注意，setPatchResultCallback 的回调是运行在 intentService 的线程中
////            .setPatchResultCallback { Logger.i("onPatchResult callback here") }
////            //设置收到后台回退要求时,锁屏清除补丁
////            //默认是等主进程重启时自动清除
////            .setPatchRollbackOnScreenOff(true)
////            //我们可以通过RollbackCallBack设置对回退时的回调
////            .setPatchRollBackCallback { Logger.i("onPatchRollback callback here") }
////    }
//
//    /**
//     * 初始化debug工具
//     */
//    private fun initDebug() {
//        //.logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
//        //.methodOffset(5)        // (Optional) Hides internal method calls up to offset. Default 5
//        val formatStrategy = PrettyFormatStrategy.newBuilder()
//            // (Optional) Whether to show thread info or not. Default true
//            .showThreadInfo(false)
//            // (Optional) How many method line to show. Default 2
//            .methodCount(2)
//            // (Optional) Global tag for every log. Default PRETTY_LOGGER
//            .tag("Flux")
//            .build()
//        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
//            override fun isLoggable(priority: Int, tag: String?): Boolean {
//                return BuildConfig.DEBUG
//            }
//        })
//    }
//
//    /**
//     * 初始化X5内核
//     */
//    private fun initX5() {
//        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
//        val cb = object : QbSdk.PreInitCallback {
//            override fun onViewInitFinished(arg0: Boolean) {
//                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
//                Logger.d("onViewInitFinished is $arg0")
//            }
//
//            override fun onCoreInitFinished() {
//                Logger.d("onCoreInitFinished")
//            }
//        }
//        //x5内核初始化接口
//        QbSdk.initX5Environment(context, cb)
//    }
//}
