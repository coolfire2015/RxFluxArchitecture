package com.huyingbao.core.arch.model

import androidx.collection.ArrayMap

/**
 * action封装类，用于传递数据，使用Builder创建
 * Created by liujunfeng on 2019/1/1.
 */
class RxAction private constructor(tag: String, val data: ArrayMap<String, Any>?) : RxEvent(tag) {

    operator fun <T> get(tag: String): T {
        return data!![tag] as T
    }

    fun <T> getResponse(): T {
        return get(RESPONSE)
    }

    fun <T> setResponse(response: T) {
        data!![RESPONSE] = response
    }

    override fun equals(obj: Any?): Boolean {
        if (this === obj) return true
        if (obj !is RxAction) return false
        val rxAction = obj as RxAction?
        return if (tag != rxAction!!.tag) false else !if (data != null) data != rxAction.data else rxAction.data != null
    }

    override fun hashCode(): Int {
        var result = tag.hashCode()
        result = 31 * result + (data?.hashCode() ?: 0)
        return result
    }

    class Builder(private val mTag: String) {
        private val mData: ArrayMap<String, Any>

        init {
            this.mData = ArrayMap()
        }

        fun put(key: String, value: Any?): Builder {
            if (value != null) mData[key] = value
            return this
        }

        fun build(): RxAction {
            return RxAction(mTag, mData)
        }
    }

    companion object {
        private val RESPONSE = "response"
    }
}