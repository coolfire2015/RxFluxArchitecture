package com.huyingbao.core.arch.module

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.huyingbao.core.arch.store.RxStoreFactory
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * 依赖注入仓库，提供 1.Application， 2.[ViewModelProvider.Factory]实现类[RxStoreFactory]
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Module
abstract class RxFluxModule {
    @Singleton
    @Binds
    abstract fun bindApplication(application: Application): Context

    @Singleton
    @Binds
    abstract fun bindViewModelFactory(rxStoreFactory: RxStoreFactory): ViewModelProvider.Factory
}

