package com.huyingbao.core.arch.store

import android.util.Log
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.huyingbao.core.arch.FluxLifecycle
import com.huyingbao.core.arch.dispatcher.Dispatcher
import com.huyingbao.core.arch.model.Change


/**
 * 继承[ViewModel]，在屏幕旋转或配置更改引起的Activity/Fragment重建时存活下来，其持有数据可以继续使用。
 *
 * 在[ViewModel.onCleared]时，取消[Dispatcher]中订阅
 *
 * Created by liujunfeng on 2020/8/1.
 */
abstract class Store() : ViewModel() {
    /**
     * 取消[Dispatcher]中订阅
     */
    @CallSuper
    override fun onCleared() {
        super.onCleared()
        Log.i(FluxLifecycle.TAG, "Unsubscribe Store : " + javaClass.simpleName)
        Dispatcher.unsubscribeStore(this)
    }

    fun postChange(change: Change) {
        Dispatcher.postChange(change)
    }
}
