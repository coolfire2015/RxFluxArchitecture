package com.huyingbao.core.arch.lifecycle

import android.app.Activity
import android.util.Log

import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.core.arch.view.RxFluxView

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel

/**
 * Created by liujunfeng on 2018/12/13.
 */
class RxActivityLifecycleObserver(private val mActivity: Activity) : LifecycleObserver {

    /**
     * activity创建成功之后调用,
     * 若activity是RxViewDispatch的子类,
     * 获取需要关联的RxStoreList
     * 将RxStoreList同activity生命周期关联
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    internal fun onCreate() {
        Log.v("RxFlux", "1.2-onCreateLifecycle ")
        if (mActivity is RxFluxView<*>) {
            val rxStore = (mActivity as RxFluxView<*>).rxStore
            if (rxStore is RxActivityStore) {
                (mActivity as FragmentActivity).lifecycle.addObserver((rxStore as RxActivityStore?)!!)
            }
        }
    }
}
