package com.huyingbao.module.second.module

/**
 * Created by liujunfeng on 2019/1/1.
 */
import androidx.lifecycle.ViewModel
import com.huyingbao.core.arch.store.RxStoreKey
import com.huyingbao.module.second.ui.store.SecondStore
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class SecondStoreModule {
    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(SecondStore::class)
    abstract fun bindSecondStore(secondStore: SecondStore): ViewModel
}
