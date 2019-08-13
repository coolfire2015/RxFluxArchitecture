package com.huyingbao.module.first.app

import android.app.Application

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent

import com.huyingbao.core.annotations.RxAppObserver
import com.huyingbao.core.arch.RxAppLifecycle
import com.huyingbao.core.arch.utils.RxAndroidInjection
import com.huyingbao.module.demo.FirstEventBusIndex
import dagger.android.HasAndroidInjector
import org.greenrobot.eventbus.EventBus

import javax.inject.Inject

@RxAppObserver
class FirstAppLifecycle(application: Application) : RxAppLifecycle(application) {
    @Inject
    lateinit var firstAppStore: FirstAppStore

    init {
        if (application is HasAndroidInjector) {
            RxAndroidInjection.inject(this, application)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onCreate() {
        EventBus.builder()
                .addIndex(FirstEventBusIndex())
                .eventInheritance(false)
        firstAppStore.subscribe()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onLowMemory() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onTerminate() {
        firstAppStore.unsubscribe()
    }
}
