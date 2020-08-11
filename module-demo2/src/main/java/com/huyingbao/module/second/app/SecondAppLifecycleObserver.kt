package com.huyingbao.module.second.app

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.huyingbao.core.annotations.AppObserver
import com.huyingbao.core.arch.lifecycle.AppLifecycleObserver
import com.huyingbao.module.demo.SecondEventBusIndex
import dagger.hilt.android.qualifiers.ApplicationContext
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

@AppObserver
class SecondAppLifecycleObserver @Inject constructor(
        @ApplicationContext private val context: Context
) : AppLifecycleObserver() {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onCreate() {
        EventBus.builder()
                .addIndex(SecondEventBusIndex())
                .eventInheritance(false)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onLowMemory() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onTerminate() {
    }
}
