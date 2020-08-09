package com.huyingbao.core.arch.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent


/**
 * Application生命周期观察者，用于子模块中观察Application的生命周期。
 *
 * 跟随Application生命周期，子类需要使用[com.huyingbao.core.annotations.AppLifecycleObserver]注解标注。
 *
 * Created by liujunfeng on 2019/1/1.
 */
abstract class RxAppLifecycle() : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    abstract fun onCreate()

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    abstract fun onLowMemory()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    abstract fun onTerminate()
}
