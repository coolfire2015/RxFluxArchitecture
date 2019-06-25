package com.huyingbao.core.arch.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 绑定[LifecycleOwner]子类生命周期，销毁时自动清理数据
 *
 * A lazy property that gets cleaned up when the lifecycleOwner is destroyed.
 *
 * Accessing this variable in a destroyed lifecycleOwner will throw NPE.
 */
class AutoClearedValue<T : Any?>(private val lifecycleOwner: LifecycleOwner) : ReadWriteProperty<LifecycleOwner, T?> {
    private var _value: T? = null

    init {
        lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                _value = null
            }
        })
    }

    override fun getValue(thisRef: LifecycleOwner, property: KProperty<*>): T? {
        return _value
    }

    override fun setValue(thisRef: LifecycleOwner, property: KProperty<*>, value: T?) {
        _value = value
    }
}

/**
 * Creates an [AutoClearedValue] associated with this lifecycleOwner.
 */
fun <T : Any?> LifecycleOwner.autoCleared() = AutoClearedValue<T?>(this)