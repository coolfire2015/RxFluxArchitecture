package com.huyingbao.core.arch.lifecycle

import android.util.Log

import com.huyingbao.core.arch.store.RxFragmentStore
import com.huyingbao.core.arch.view.RxFluxView

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel

/**
 * Created by liujunfeng on 2018/12/13.
 */
class RxFragmentLifecycleObserver(private val mFragment: Fragment) : LifecycleObserver {

    /**
     * fragment创建成功之后调用,
     * 若fragment是RxViewDispatch的子类,
     * 获取需要关联的RxStoreList
     * 将RxStoreList同fragment生命周期关联
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    internal fun onCreate() {
        Log.v("RxFlux", "5.1-onCreateFragment")
        if (mFragment is RxFluxView<*>) {
            val rxStore = (mFragment as RxFluxView<*>).rxStore
            if (rxStore is RxFragmentStore) {
                mFragment.lifecycle.addObserver((rxStore as RxFragmentStore?)!!)
            }
        }
    }
}