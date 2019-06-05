package com.huyingbao.module.github.app

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.huyingbao.core.annotations.RxAppObserver
import com.huyingbao.core.arch.RxAppLifecycle
import com.huyingbao.module.github.GithubEventBusIndex
import org.greenrobot.eventbus.EventBus

/**
 * Created by liujunfeng on 2019/5/30.
 */
@RxAppObserver
class GithubAppLifecyle(application: Application?) : RxAppLifecycle(application) {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        EventBus.builder()
                .addIndex(GithubEventBusIndex())
                .eventInheritance(false)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {

    }
}
