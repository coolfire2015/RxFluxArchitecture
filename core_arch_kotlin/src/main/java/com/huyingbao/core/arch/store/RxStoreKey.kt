package com.huyingbao.core.arch.store

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Qualifier注解用来区分同一纬度下两种不同的创建实例的方法
 * 区分不同的RxStore子类 对ClassKey注解的扩展
 * Created by liujunfeng on 2019/1/1.
 */
@Documented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(RetentionPolicy.RUNTIME)
@MapKey
annotation class RxStoreKey(val value: KClass<out ViewModel>)
