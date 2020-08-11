package com.huyingbao.core.arch.model

import android.text.TextUtils
import androidx.collection.ArrayMap
import org.greenrobot.eventbus.EventBusEvent

/**
 * 操作结果通知，封装操作返回数据。
 *
 * 由[com.huyingbao.core.arch.action.ActionCreator]发送到[com.huyingbao.core.arch.store.Store]，
 *
 * Created by liujunfeng on 2019/1/1.
 */
class Action private constructor(
        tag: String,
        val data: ArrayMap<String, Any>
) : EventBusEvent(tag) {
    /**
     * 获取ArrayMap中Key对应的Value
     */
    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(tag: String): T? {
        return data[tag] as T?
    }

    /**
     * 获取ArrayMap中存储的操作结果
     */
    fun <T> getResponse(): T? {
        return get("response")
    }

    /**
     * 操作结果存储到ArrayMap中
     */
    fun <T> setResponse(response: T) {
        data["response"] = response
    }

    override fun equals(other: Any?): Boolean {
        return this === other || (other != null
                && other is Action
                && TextUtils.equals(tag, other.tag)
                && data == other.data)
    }

    override fun hashCode(): Int {
        var result = tag.hashCode()
        result = 31 * result + data.hashCode()
        return result
    }

    /**
     * 构造器
     */
    class Builder(private val tag: String) {
        private val data: ArrayMap<String, Any> = ArrayMap()

        /**
         * 添加数据
         */
        fun put(key: String, value: Any?): Builder {
            if (value != null) {
                data[key] = value
            }
            return this
        }

        /**
         * 构造对象
         */
        fun build(): Action {
            return Action(tag, data)
        }
    }
}