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
 * 全局生命周期跟随类
 *
 * Created by liujunfeng on 2019/5/30.
 */
@RxAppObserver
class GithubAppLifecycle(application: Application) : RxAppLifecycle(application) {
    init {
        RxAndroidInjection.inject(this, application)
    }

    @Inject
    lateinit var githubAppStore: GithubAppStore

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onCreate() {
        //EventBus使用kapt编译生成的索引文件
        EventBus.builder()
                .addIndex(GithubEventBusIndex())
                .eventInheritance(false)
        //全局数据维持AppStore，注册订阅
        githubAppStore.subscribe()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onLowMemory() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onTerminate() {
        //全局数据维持AppStore，取消订阅
        githubAppStore.unsubscribe()
    }
}
