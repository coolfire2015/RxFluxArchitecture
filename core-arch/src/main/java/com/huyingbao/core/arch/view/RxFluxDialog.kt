package com.huyingbao.core.arch.view

import android.content.Context
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.core.arch.store.RxFragmentStore
import com.huyingbao.core.arch.utils.ClassUtils

import javax.inject.Inject

import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection

/**
 * Created by liujunfeng on 2019/1/1.
 */
abstract class RxFluxDialog<T : ViewModel> : AppCompatDialogFragment(), RxFluxView<T>, RxSubscriberView, HasAndroidInjector {
    private var mStore: T? = null
    @Inject
    internal var mViewModelFactory: ViewModelProvider.Factory? = null
    @Inject
    internal var mChildAndroidInjector: DispatchingAndroidInjector<Any>? = null

    override val rxStore: T?
        get() {
            if (mStore != null) {
                return mStore
            }
            val storeClass = ClassUtils.getGenericClass<T>(javaClass)
                    ?: throw IllegalArgumentException("No generic class for Class<" + javaClass.getCanonicalName() + ">")
            if (storeClass.superclass == RxActivityStore::class.java) {
                mStore = ViewModelProviders.of(activity!!, mViewModelFactory).get(storeClass)
            } else if (storeClass.superclass == RxFragmentStore::class.java) {
                mStore = ViewModelProviders.of(this, mViewModelFactory).get(storeClass)
            }
            return mStore
        }

    override fun androidInjector(): AndroidInjector<Any>? {
        return mChildAndroidInjector
    }

    /**
     * 子类都需要在Module中使用dagger.android中的
     * [dagger.android.ContributesAndroidInjector]注解
     * 生成对应的注入器，在方法中进行依赖注入操作。
     *
     * @param context
     */
    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    /**
     * View在destroy时,不再持有该Store对象
     */
    override fun onDestroy() {
        mStore = null
        super.onDestroy()
    }
}