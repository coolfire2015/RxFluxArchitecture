package com.huyingbao.module.common.module

import androidx.lifecycle.ViewModel
import com.huyingbao.core.arch.store.RxStoreKey
import com.huyingbao.module.common.ui.start.store.StartStore
import com.huyingbao.module.common.ui.web.store.CommonWebStore
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

/**
 * Store仓库
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Module
abstract class LoadingStoreModule {
    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(StartStore::class)
    abstract fun bindStartStore(startStore: StartStore): ViewModel

    @Singleton
    @Binds
    @IntoMap
    @RxStoreKey(CommonWebStore::class)
    abstract fun bindCommonWebStore(commonWebStore: CommonWebStore): ViewModel
}
