package com.huyingbao.core.arch.store

import android.util.Log

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel

import com.huyingbao.core.arch.RxFlux
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxChange
import javax.inject.Inject


/**
 * 继承[ViewModel]，在屏幕旋转或配置更改引起的Fragment重建时存活下来，其持有数据可以继续使用。
 *
 * 实现[LifecycleObserver]，关联生命周期。
 *
 * Created by liujunfeng on 2020/8/1.
 */
abstract class RxStoreImpl() : ViewModel(), LifecycleObserver, RxStore {
    @Inject
    open lateinit var rxDispatcher: RxDispatcher

    /**
     * 在所关联对象（Fragment）[Lifecycle.Event.ON_CREATE]时，注册到[RxDispatcher]
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun subscribe() {
        if (rxDispatcher.isSubscribe(this)) {
            return
        }
        Log.i(RxFlux.TAG, "Subscribe RxStore : " + javaClass.simpleName)
        rxDispatcher.subscribeRxStore(this)
    }


    /**
     * 在所关联对象（Fragment）[Lifecycle.Event.ON_DESTROY]时，取消[RxDispatcher]中订阅
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun unsubscribe() {
        Log.i(RxFlux.TAG, "Unsubscribe RxStore : " + javaClass.simpleName)
        rxDispatcher.unsubscribeRxStore(this)
    }

    override fun postChange(rxChange: RxChange) {
        rxDispatcher.postRxChange(rxChange)
    }
}
