package com.huyingbao.core.arch

import android.app.Application

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * 跟随Application生命周期
 *
 *
 * Created by liujunfeng on 2019/1/1.
 */
abstract class RxAppLifecycle(protected var mApplication: Application) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    abstract fun onCreate()

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    abstract fun onLowMemory()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    abstract fun onTerminate()
}
