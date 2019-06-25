package com.huyingbao.module.wan.app

import android.app.Application

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent

import com.huyingbao.core.annotations.RxAppObserver
import com.huyingbao.core.arch.RxAppLifecycle
import com.huyingbao.core.arch.utils.RxAndroidInjection
import com.huyingbao.module.wan.WanEventBusIndex

import org.greenrobot.eventbus.EventBus

import javax.inject.Inject

@RxAppObserver
class WanAppLifecycle(application: Application) : RxAppLifecycle(application) {
    @Inject
    lateinit var wanAppStore: WanAppStore

    init {
        RxAndroidInjection.inject(this, application)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onCreate() {
        EventBus.builder()
                .addIndex(WanEventBusIndex())
                .eventInheritance(false)
        wanAppStore.subscribe()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onLowMemory() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onTerminate() {
        wanAppStore.unsubscribe()
    }
}
