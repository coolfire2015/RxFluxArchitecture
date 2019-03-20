package com.huyingbao.core.arch


import dagger.android.support.DaggerApplication
import javax.inject.Inject

/**
 * Application实现相应的接口
 * HasActivityInjector、
 * HasFragmentInjector、
 * HasSupportFragmentInjector、
 * HasServiceInjector、
 * HasBroadcastReceiverInjector
 * Created by liujunfeng on 2019/1/1.
 */
abstract class RxFluxApp : DaggerApplication() {
    @Inject
    lateinit var mRxFlux: RxFlux

    override fun onCreate() {
        super.onCreate()
        //application创建的时候调用该方法，
        //使RxFlux可以接受Activity生命周期回调
        registerActivityLifecycleCallbacks(mRxFlux)
    }
}
