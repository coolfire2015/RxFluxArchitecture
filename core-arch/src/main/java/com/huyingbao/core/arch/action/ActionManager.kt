package com.huyingbao.core.arch.action

import androidx.collection.ArrayMap
import androidx.core.util.Pair
import com.huyingbao.core.arch.model.Action
import kotlinx.coroutines.Job


/**
 * 订阅管理类：关联并管理[Action]与[Job]
 *
 * Created by liujunfeng on 2020/8/1.
 */
class ActionManager {
    /**
     * 管理订阅的ArrayMap，[Action]的tag作为key
     */
    private val arrayMap: ArrayMap<String, Pair<Int, Job>> = ArrayMap()

    /**
     * 成对添加[Action]和[Job]到管理订阅的ArrayMap中。
     * 如果已存在，则取消[Job]中观察者与被观察者订阅关系，
     * 停止被观察者[kotlinx.coroutines.flow.Flow]方法。
     */
    fun add(action: Action, job: Job) {
        val old = arrayMap.put(action.tag, getPair(action, job))
        if (old?.second != null && !old.second!!.isCompleted) {
            old.second!!.cancel()
        }
    }

    /**
     * 移除[Action]，取消[Job]中观察者与被观察者订阅关系，
     * 停止被观察者[io.reactivex.Observable]方法。
     */
    fun remove(action: Action) {
        val old = arrayMap.remove(action.tag)
        if (old?.second != null && !old.second!!.isCompleted) {
            old.second!!.cancel()
        }
    }

    /**
     * 检查否已存在[Action]
     */
    operator fun contains(action: Action): Boolean {
        val old = arrayMap[action.tag]
        return (old?.first != null
                && old.first == action.hashCode()
                && old.second != null
                && !old.second!!.isCompleted)
    }

    /**
     * 清除所有的[Job]
     */
    @Synchronized
    fun clear() {
        if (arrayMap.isEmpty) return
        for (pair in arrayMap.values) {
            if (pair.second != null && !pair.second!!.isActive) {
                pair.second!!.cancel()
            }
        }
    }

    /**
     * 创建一个新的[Pair]
     *
     * @param action hashcode作为Key
     */
    private fun getPair(action: Action, job: Job): Pair<Int, Job> {
        return Pair(action.hashCode(), job)
    }
}
