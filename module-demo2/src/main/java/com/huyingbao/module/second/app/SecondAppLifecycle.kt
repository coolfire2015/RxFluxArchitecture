package com.huyingbao.module.second.app

import android.app.Application

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent

import com.huyingbao.core.annotations.RxAppObserver
import com.huyingbao.core.arch.RxAppLifecycle
import com.huyingbao.core.arch.utils.RxAndroidInjection
import com.huyingbao.module.demo.SecondEventBusIndex
import dagger.android.HasAndroidInjector
import org.greenrobot.eventbus.EventBus

import javax.inject.Inject

@RxAppObserver
class SecondAppLifecycle(
        application: Application
) : RxAppLifecycle(application) {
    @Inject
    lateinit var secondAppStore: SecondAppStore

    init {
        if (application is HasAndroidInjector) {
            RxAndroidInjection.inject(this, application)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onCreate() {
        EventBus.builder()
                .addIndex(SecondEventBusIndex())
                .eventInheritance(false)
        secondAppStore.subscribe()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onLowMemory() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onTerminate() {
        secondAppStore.unsubscribe()
    }
}