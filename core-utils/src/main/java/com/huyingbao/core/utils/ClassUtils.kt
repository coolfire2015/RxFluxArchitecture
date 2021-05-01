package com.huyingbao.core.utils

import java.lang.reflect.ParameterizedType

/**
 * Class工具类
 *
 * Created by liujunfeng on 2019/1/1.
 */
object ClassUtils {
    /**
     * 得到类的泛型类型
     */
    @JvmStatic
    @SuppressWarnings("unchecked")
    fun <T> getGenericClass(aClass: Class<*>, index: Int): Class<T>? {
        var genericClass: Class<T>? = null
        //返回直接继承的父类（包含泛型参数）
        val genericSuperclass = aClass.genericSuperclass
        if (genericSuperclass is ParameterizedType) {
            try {
                genericClass = genericSuperclass.actualTypeArguments[index] as Class<T>
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return genericClass
    }
}