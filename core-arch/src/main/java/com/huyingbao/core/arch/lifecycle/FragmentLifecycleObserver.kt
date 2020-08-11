package com.huyingbao.core.arch.lifecycle

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.huyingbao.core.arch.store.FragmentStore
import com.huyingbao.core.arch.view.FluxView

/**
 * Fragment生命周期观察者，将Fragment持有的[com.huyingbao.core.arch.store.Store]关联其生命周期。
 *
 * Created by liujunfeng on 2019/1/1.
 */
class FragmentLifecycleObserver(
        private val fragment: Fragment
) : LifecycleObserver {
    /**
     * 在onAttach()完成依赖注入之后调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        if (fragment is FluxView<*>) {
            val rxStore = fragment.rxStore
            if (rxStore is FragmentStore) {
                //rxStore关联Fragment生命周期
                fragment.lifecycle.addObserver(rxStore)
            }
        }
    }
}
