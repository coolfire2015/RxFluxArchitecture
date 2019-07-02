package com.huyingbao.core.arch.action

import androidx.collection.ArrayMap
import androidx.core.util.Pair
import com.huyingbao.core.arch.model.RxAction
import io.reactivex.disposables.Disposable


/**
 * 订阅管理类：关联并管理[RxAction]与[Disposable]
 *
 * Created by liujunfeng on 2019/1/1.
 */
class RxActionManager {
    /**
     * 管理订阅的ArrayMap，[RxAction]的tag作为key
     */
    private val arrayMap: ArrayMap<String, Pair<Int, Disposable>> = ArrayMap()

    /**
     * 成对添加[RxAction]和[Disposable]到管理订阅的ArrayMap中。
     * 如果已存在，则取消[Disposable]中观察者与被观察者订阅关系，
     * 停止被观察者[io.reactivex.Observable]方法。
     */
    fun add(rxAction: RxAction, disposable: Disposable) {
        val old = arrayMap.put(rxAction.tag, getPair(rxAction, disposable))
        if (old?.second != null && !old.second!!.isDisposed) {
            old.second!!.dispose()
        }
    }

    /**
     * 移除[RxAction]，取消[Disposable]中观察者与被观察者订阅关系，
     * 停止被观察者[io.reactivex.Observable]方法。
     */
    fun remove(rxAction: RxAction) {
        val old = arrayMap.remove(rxAction.tag)
        if (old?.second != null && !old.second!!.isDisposed) {
            old.second!!.dispose()
        }
    }

    /**
     * 检查否已存在[RxAction]
     */
    operator fun contains(rxAction: RxAction): Boolean {
        val old = arrayMap[rxAction.tag]
        return (old?.first != null
                && old.first == rxAction.hashCode()
                && old.second != null
                && !old.second!!.isDisposed)
    }

    /**
     * 清除所有的[Disposable]
     */
    @Synchronized
    fun clear() {
        if (arrayMap.isEmpty) {
            return
        }
        for (pair in arrayMap.values) {
            if (pair.second != null && !pair.second!!.isDisposed) {
                pair.second!!.dispose()
            }
        }
    }

    /**
     * 创建一个新的[Pair]
     *
     * @param rxAction hashcode作为Key
     */
    private fun getPair(rxAction: RxAction, disposable: Disposable): Pair<Int, Disposable> {
        return Pair(rxAction.hashCode(), disposable)
    }
}
