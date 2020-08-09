package com.huyingbao.module.third.app

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.huyingbao.core.annotations.AppLifecycleObserver
import com.huyingbao.core.arch.lifecycle.RxAppLifecycle
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AppLifecycleObserver
class ThirdAppLifecycle @Inject constructor(
        @ApplicationContext private val context: Context
) : RxAppLifecycle() {
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
