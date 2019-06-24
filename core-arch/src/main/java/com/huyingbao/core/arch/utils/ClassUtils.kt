package com.huyingbao.core.arch.utils

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by liujunfeng on 2019/1/1.
 */
object ClassUtils {

    /**
     * 得到类的泛型类型
     *
     * @param aClass
     * @param <T>
     * @return
    </T> */
    fun <T> getGenericClass(aClass: Class<*>): Class<T>? {
        var genericClass: Class<T>? = null
        //返回直接继承的父类（包含泛型参数）
        val genericSuperclass = aClass.genericSuperclass
        if (genericSuperclass is ParameterizedType) {
            try {
                genericClass = genericSuperclass.actualTypeArguments[0] as Class<T>
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return genericClass
    }
}