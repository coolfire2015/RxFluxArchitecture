package com.huyingbao.core.arch.store

import android.app.Application
import android.util.Log

import androidx.lifecycle.AndroidViewModel

import com.huyingbao.core.arch.RxFlux
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxChange


/**
 * 存储和管理Application生命周期内的数据
 *
 * Created by liujunfeng on 2019/1/1.
 */
abstract class RxAppStore(application: Application, private val rxDispatcher: RxDispatcher) : AndroidViewModel(application), RxStore {

    /**
     * 在所关联对象（Application）创建时调用该方法，注册到[RxDispatcher]
     */
    fun subscribe() {
        if (rxDispatcher.isSubscribe(this)) {
            return
        }
        Log.i(RxFlux.TAG, "Subscribe RxApStore : " + javaClass.simpleName)
        rxDispatcher.subscribeRxStore(this)
    }

    /**
     * 在所关联对象Application销毁时，取消[RxDispatcher]中订阅
     */
    fun unsubscribe() {
        Log.i(RxFlux.TAG, "Unsubscribe RxApStore : " + javaClass.simpleName)
        rxDispatcher.unsubscribeRxStore(this)
    }

    override fun postChange(rxChange: RxChange) {
        rxDispatcher.postRxChange(rxChange)
    }
}
