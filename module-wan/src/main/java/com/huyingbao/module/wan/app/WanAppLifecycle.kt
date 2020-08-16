package com.huyingbao.module.wan.app


import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.huyingbao.core.annotations.AppObserver
import com.huyingbao.core.arch.lifecycle.AppLifecycleObserver
import com.huyingbao.module.wan.WanEventBusIndex
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

/**
 * Application生命周期方法分发类
 *
 * Created by liujunfeng on 2019/8/1.
 */
@AppObserver
class WanAppLifecycle @Inject constructor() : AppLifecycleObserver() {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onCreate() {
        EventBus.builder()
                .addIndex(WanEventBusIndex())
                .eventInheritance(false)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onLowMemory() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onTerminate() {
    }
}
