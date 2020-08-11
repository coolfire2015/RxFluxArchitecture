package com.huyingbao.module.third.app

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.huyingbao.core.annotations.AppObserver
import com.huyingbao.core.arch.lifecycle.AppLifecycleObserver
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AppObserver
class ThirdAppLifecycleObserver @Inject constructor(
        @ApplicationContext private val context: Context
) : AppLifecycleObserver() {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onCreate() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onLowMemory() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onTerminate() {
    }
}
