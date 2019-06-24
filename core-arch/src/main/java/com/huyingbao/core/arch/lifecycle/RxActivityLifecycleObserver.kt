package com.huyingbao.core.arch.lifecycle

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.core.arch.view.RxFluxView

/**
 * Activity生命周期观察者，将Activity持有的[com.huyingbao.core.arch.store.RxStore]关联其生命周期。
 *
 * Created by liujunfeng on 2019/1/1.
 */
class RxActivityLifecycleObserver(private val mActivity: Activity) : LifecycleObserver {

    /**
     * 在onCreate(Bundle)完成依赖注入之后调用
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    internal fun onCreate() {
        if (mActivity is RxFluxView<*>) {
            val rxStore = (mActivity as RxFluxView<*>).rxStore
            if (rxStore is RxActivityStore) {
                //rxStore关联activity生命周期
                (mActivity as FragmentActivity).lifecycle.addObserver((rxStore as RxActivityStore?)!!)
            }
        }
    }
}
