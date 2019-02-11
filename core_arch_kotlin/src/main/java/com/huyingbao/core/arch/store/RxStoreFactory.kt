package com.huyingbao.core.arch.store

import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * 实现ViewModelProvider.Factory
 * 提供ViewModel缓存的实例
 * 通过Dagger2将Map直接注入，
 * 通过Key直接获取到对应的ViewModel
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
class RxStoreFactory @Inject
internal constructor(private val creators: Map<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = creators[modelClass]
        //通过class找到相应ViewModel的Provider
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        if (creator == null) {
            throw IllegalArgumentException("unknown model class $modelClass")
        }
        try {
            //通过get()方法获取到ViewModel
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}
