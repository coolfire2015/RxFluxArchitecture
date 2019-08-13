package com.huyingbao.module.first.module

/**
 * Created by liujunfeng on 2019/1/1.
 */
import androidx.lifecycle.ViewModel
import com.huyingbao.core.arch.store.RxStoreKey
import com.huyingbao.module.first.ui.store.FirstStore
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class FirstStoreModule {
    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(FirstStore::class)
    abstract fun bindFirstStore(firstStore: FirstStore): ViewModel
}
