package com.huyingbao.core.arch.store

import android.app.Application
import android.util.Log
import androidx.annotation.CallSuper
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.huyingbao.core.arch.FluxCallback
import com.huyingbao.core.arch.dispatcher.FluxDispatcher
import com.huyingbao.core.arch.dispatcher.FluxSubscriber

/**
 * 0.继承[AndroidViewModel]，内部持有[Application]
 *
 * 1.存储和管理Application生命周期内的数据
 *
 * 2.接收[FluxDispatcher]发送的数据。
 */
abstract class FluxAppStore(
    @NonNull application: Application
) : AndroidViewModel(application), DefaultLifecycleObserver, FluxSubscriber {
    @CallSuper
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        Log.i(FluxCallback.TAG, "Subscribe Store : " + javaClass.simpleName)
        FluxDispatcher.subscribe(this)
    }

    @CallSuper
    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Log.i(FluxCallback.TAG, "Unsubscribe Store : " + javaClass.simpleName)
        FluxDispatcher.unsubscribe(this)
    }

    @CallSuper
    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        Log.i(FluxCallback.TAG, "Unsubscribe Store : " + javaClass.simpleName)
        FluxDispatcher.unsubscribe(this)
    }
}