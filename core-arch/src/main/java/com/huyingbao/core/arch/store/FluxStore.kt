package com.huyingbao.core.arch.store

import android.util.Log
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.huyingbao.core.arch.FluxCallbacks
import com.huyingbao.core.arch.dispatcher.FluxDispatcher
import com.huyingbao.core.arch.dispatcher.FluxSubscriber
import com.huyingbao.core.arch.model.Change


/**
 * Store层
 *
 * 继承[ViewModel]，在屏幕旋转或配置更改引起的Activity/Fragment重建时存活下来，其持有数据可以继续使用。
 *
 * 继承[FluxSubscriber]，接受总线事件。
 *
 * Created by liujunfeng on 2020/8/1.
 */
abstract class FluxStore : ViewModel(), FluxSubscriber {
    /**
     * 取消[FluxDispatcher]中订阅
     */
    @CallSuper
    override fun onCleared() {
        super.onCleared()
        Log.i(FluxCallbacks.TAG, "Unsubscribe Store : " + javaClass.simpleName)
        FluxDispatcher.unsubscribe(this)
    }

    fun postChange(change: Change) {
        FluxDispatcher.postChange(change)
    }
}
