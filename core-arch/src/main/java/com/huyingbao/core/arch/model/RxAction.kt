package com.huyingbao.core.arch.model

import android.text.TextUtils
import androidx.collection.ArrayMap
import org.greenrobot.eventbus.EventBusEvent

/**
 * 操作结果通知，封装操作返回数据。
 *
 * 由[com.huyingbao.core.arch.action.RxActionCreator]发送到[com.huyingbao.core.arch.store.RxStore]，
 *
 * Created by liujunfeng on 2019/1/1.
 */
class RxAction private constructor(
        tag: String,
        val data: ArrayMap<String, Any>
) : EventBusEvent(tag) {

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(tag: String): T? {
        return data[tag] as T?
    }

    fun <T> getResponse(): T? {
        return get(RESPONSE)
    }

    fun <T> setResponse(response: T) {
        data[RESPONSE] = response
    }

    override fun equals(other: Any?): Boolean {
        return this === other || (other != null
                && other is RxAction
                && TextUtils.equals(mTag, other.tag)
                && data == other.data)
    }

    override fun hashCode(): Int {
        var result = mTag.hashCode()
        result = 31 * result + data.hashCode()
        return result
    }

    class Builder(private val tag: String) {
        private val data: ArrayMap<String, Any> = ArrayMap()

        fun put(key: String, value: Any?): Builder {
            if (value != null) {
                data[key] = value
            }
            return this
        }

        fun build(): RxAction {
            return RxAction(tag, data)
        }
    }

    companion object {
        private const val RESPONSE = "response"
    }
}