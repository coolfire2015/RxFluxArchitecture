package com.huyingbao.module.github.app

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.huyingbao.core.annotations.RxAppObserver
import com.huyingbao.core.arch.RxAppLifecycle
import com.huyingbao.core.arch.utils.RxAndroidInjection
import com.huyingbao.module.github.GithubEventBusIndex
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

/**
 * Created by liujunfeng on 2019/5/30.
 */
@RxAppObserver
class GithubAppLifecycle(application: Application?) : RxAppLifecycle(application) {
    init {
        RxAndroidInjection.inject(this, mApplication)
    }

    @Inject
    lateinit var githubAppStore: GithubAppStore

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onCreate() {
        EventBus.builder()
                .addIndex(GithubEventBusIndex())
                .eventInheritance(false)
        githubAppStore.subscribe()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onLowMemory() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onTerminate() {
        githubAppStore.unsubscribe()
    }
}
