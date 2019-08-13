package com.huyingbao.module.third.module

/**
 * Created by liujunfeng on 2019/1/1.
 */
import androidx.lifecycle.ViewModel
import com.huyingbao.core.arch.store.RxStoreKey
import com.huyingbao.module.third.ui.store.ThirdStore
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ThirdStoreModule {
    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(ThirdStore::class)
    abstract fun bindThirdStore(thirdStore: ThirdStore): ViewModel
}
