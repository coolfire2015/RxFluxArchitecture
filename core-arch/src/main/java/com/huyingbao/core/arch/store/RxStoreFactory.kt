package com.huyingbao.core.arch.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * 实现[ViewModelProvider.Factory]，提供过依赖注入获取[RxStore] Map集合， 缓存并对外提供[RxStore]实例
 *
 *
 * Created by liujunfeng on 2019/1/1.
 */

@Singleton
class RxStoreFactory @Inject
internal constructor(private val mClassProviderMap: Map<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var viewModelProvider: Provider<out ViewModel>? = mClassProviderMap[modelClass]
        //通过class找到相应ViewModel的Provider
        if (viewModelProvider == null) {
            for ((key, value) in mClassProviderMap) {
                if (modelClass.isAssignableFrom(key)) {
                    viewModelProvider = value
                    break
                }
            }
        }
        if (viewModelProvider == null) {
            throw IllegalArgumentException("unknown model class $modelClass")
        }
        try {
            //通过get()方法获取到ViewModel
            return viewModelProvider.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}
