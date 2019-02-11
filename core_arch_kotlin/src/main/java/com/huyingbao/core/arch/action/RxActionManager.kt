package com.huyingbao.core.arch.action

import com.huyingbao.core.arch.model.RxAction

import javax.inject.Inject
import javax.inject.Singleton

import androidx.collection.ArrayMap
import androidx.core.util.Pair
import io.reactivex.disposables.Disposable


/**
 * 订阅管理类
 * 将action与action处理事件连接起来，
 * action的tag作为key
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
class RxActionManager @Inject
internal constructor() {
    /**
     * 管理订阅的ArrayMap
     * action的tag作为key
     */
    private val mMap: ArrayMap<String, Pair<Int, Disposable>>

    init {
        mMap = ArrayMap()
    }

    /**
     * 添加一个action和disposable,如果已经有了一个对应action 的订阅,则取消订阅
     */
    fun add(action: RxAction, disposable: Disposable) {
        val old = mMap.put(action.tag, getPair(action, disposable))
        if (old != null && old.second != null && !old.second!!.isDisposed)
            old.second!!.dispose()
    }

    /**
     * 从管理器中取消订阅
     */
    fun remove(action: RxAction) {
        val old = mMap.remove(action.tag)
        if (old != null && old.second != null && !old.second!!.isDisposed)
            old.second!!.dispose()
    }

    /**
     * 检查action是否已经运行一个disposable
     */
    operator fun contains(action: RxAction): Boolean {
        val old = mMap[action.tag]
        return (old != null
                && old.first != null
                && old.first == action.hashCode()
                && old.second != null
                && !old.second!!.isDisposed)
    }

    /**
     * 清除所有的disposables
     */
    @Synchronized
    fun clear() {
        if (mMap.isEmpty) return
        for (pair in mMap.values)
            if (pair.second != null && !pair.second!!.isDisposed)
                pair.second!!.dispose()
    }

    /**
     * 创建一个新的pair
     *
     * @param action     转变成hashcode
     * @param disposable
     * @return
     */
    private fun getPair(action: RxAction, disposable: Disposable): Pair<Int, Disposable> {
        return Pair(action.hashCode(), disposable)
    }
}
