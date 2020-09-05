package com.huyingbao.core.arch.store

import android.util.Log

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel

import com.huyingbao.core.arch.FluxLifecycleCallback
import com.huyingbao.core.arch.dispatcher.Dispatcher
import com.huyingbao.core.arch.model.Change


/**
 * 继承[ViewModel]，在屏幕旋转或配置更改引起的Fragment重建时存活下来，其持有数据可以继续使用。
 *
 * 实现[LifecycleObserver]，关联生命周期。
 *
 * Created by liujunfeng on 2020/8/1.
 */
abstract class StoreImpl(
        private val dispatcher: Dispatcher
) : ViewModel(), LifecycleObserver, Store {
    /**
     * 在所关联对象（Fragment）[Lifecycle.Event.ON_CREATE]时，注册到[Dispatcher]
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun subscribe() {
        if (dispatcher.isSubscribe(this)) {
            return
        }
        Log.i(FluxLifecycleCallback.TAG, "Subscribe Store : " + javaClass.simpleName)
        dispatcher.subscribeStore(this)
    }


    /**
     * 在所关联对象（Fragment）[Lifecycle.Event.ON_DESTROY]时，取消[Dispatcher]中订阅
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun unsubscribe() {
        Log.i(FluxLifecycleCallback.TAG, "Unsubscribe Store : " + javaClass.simpleName)
        dispatcher.unsubscribeStore(this)
    }

    override fun postChange(change: Change) {
        dispatcher.postChange(change)
    }
}
