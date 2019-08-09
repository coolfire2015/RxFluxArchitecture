package com.huyingbao.module.common.app

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.huyingbao.core.annotations.RxAppObserver
import com.huyingbao.core.arch.RxAppLifecycle
import com.huyingbao.core.arch.utils.RxAndroidInjection

/**
 * 全局生命周期跟随类
 *
 * Created by liujunfeng on 2019/5/30.
 */
@RxAppObserver
class CommonAppLifecycle(application: Application) : RxAppLifecycle(application) {
    init {
        RxAndroidInjection.inject(this, application)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onCreate() {
        //EventBus使用kapt编译生成的索引文件
//        EventBus.builder()
//                .addIndex(CommonEventBusIndex())
//                .eventInheritance(false)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onLowMemory() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onTerminate() {
    }
}
