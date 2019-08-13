package com.huyingbao.core.arch.view

import android.content.Context
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.core.arch.store.RxFragmentStore
import com.huyingbao.core.arch.utils.ClassUtils
import com.huyingbao.core.arch.utils.autoCleared
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * 实现[HasAndroidInjector]，
 * 使用Dagger.Android实现依赖注入。
 *
 * 实现[com.huyingbao.core.arch.view.RxFluxView]，
 * 持有依赖注入的[com.huyingbao.core.arch.store.RxStore]对象，
 * 并绑定到自身生命周期。
 *
 * 实现[com.huyingbao.core.arch.view.RxSubscriberView]，
 * 根据自身生命周期，自动注册订阅或者取消订阅。
 *
 * Created by liujunfeng on 2019/1/1.
 */
abstract class RxFluxDialog<T : ViewModel> :
        AppCompatDialogFragment(),
        RxFluxView<T>,
        RxSubscriberView,
        HasAndroidInjector {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var childAndroidInjector: DispatchingAndroidInjector<Any>

    private var store by autoCleared<T?>()

    override val rxStore: T?
        get() {
            if (store != null) {
                return store
            }
            val storeClass = ClassUtils.getGenericClass<T>(javaClass)
                    ?: throw IllegalArgumentException("No generic class for Class<" + javaClass.canonicalName + ">")
            if (storeClass.superclass == RxActivityStore::class.java) {
                store = ViewModelProviders.of(activity!!, viewModelFactory).get(storeClass)
            } else if (storeClass.superclass == RxFragmentStore::class.java) {
                store = ViewModelProviders.of(this, viewModelFactory).get(storeClass)
            }
            return store
        }

    override fun androidInjector(): AndroidInjector<Any>? {
        return childAndroidInjector
    }

    /**
     * 子类都需要在Module中使用dagger.android中的
     * [dagger.android.ContributesAndroidInjector]注解
     * 生成对应的注入器，在方法中进行依赖注入操作。
     */
    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}