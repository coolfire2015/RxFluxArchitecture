//package com.huyingbao.core.utils
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.viewbinding.ViewBinding
//
//object ViewBindingUtils {
//
//    /**
//     * 生成ViewBinding,主要给Fragment使用
//     */
//    fun <VB : ViewBinding> inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
//            ClassUtils.getGenericClass<VB>(javaClass, 0)
//                    ?.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
//                    ?.invoke(null, inflater, container, false)
//                    as VB
//
//    /**
//     * 生成ViewBinding,主要给Activity使用
//     */
//    fun <VB : ViewBinding> inflateViewBinding(inflater: LayoutInflater) =
//            ClassUtils.getGenericClass<VB>(javaClass, 0)
//                    ?.getMethod("inflate", LayoutInflater::class.java)
//                    ?.invoke(null, inflater)
//                    as VB
//
//}
//
