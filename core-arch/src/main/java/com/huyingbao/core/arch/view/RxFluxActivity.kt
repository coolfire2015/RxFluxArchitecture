package com.huyingbao.core.arch.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.huyingbao.core.arch.store.RxActivityStore
import com.huyingbao.core.arch.utils.ClassUtils
import com.huyingbao.core.arch.utils.autoCleared
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


/**
 * Created by liujunfeng on 2019/1/1.
 */
abstract class RxFluxActivity<T : RxActivityStore> :
        AppCompatActivity(),
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
            store = ViewModelProviders.of(this, viewModelFactory).get(storeClass)
            return store
        }

    override fun androidInjector(): AndroidInjector<Any> {
        return childAndroidInjector
    }

    /**
     * 子类都需要在Module中使用dagger.android中的
     * [dagger.android.ContributesAndroidInjector]注解
     * 生成对应的注入器，在方法中进行依赖注入操作。
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
}