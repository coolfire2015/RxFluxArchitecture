package com.huyingbao.core.arch.store

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Qualifier注解用来区分同一纬度下两种不同的创建实例的方法，
 *
 *
 * 区分不同的[RxStore]子类 对[dagger.multibindings.ClassKey]扩展。
 *
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class RxStoreKey(val value: KClass<out ViewModel>)
