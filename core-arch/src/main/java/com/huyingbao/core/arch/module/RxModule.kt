package com.huyingbao.core.arch.module

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.store.RxStoreFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * 依赖注入仓库
 *
 * 1.提供 [Application]
 *
 * 2.提供 [ViewModelProvider.Factory]实现类[RxStoreFactory]
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Module(includes = [RxProviderModule::class])
abstract class RxFluxModule {
    @Singleton
    @Binds
    abstract fun bindApplication(application: Application): Context

    @Singleton
    @Binds
    abstract fun bindViewModelFactory(rxStoreFactory: RxStoreFactory): ViewModelProvider.Factory
}

/**
 * 提供[RxActionManager]和[RxDispatcher]
 */
@Module
class RxProviderModule {
    @Singleton
    @Provides
    fun provideRxActionManager(): RxActionManager {
        return RxActionManager()
    }

    @Singleton
    @Provides
    fun provideRxDispatcher(): RxDispatcher {
        return RxDispatcher()
    }
}
