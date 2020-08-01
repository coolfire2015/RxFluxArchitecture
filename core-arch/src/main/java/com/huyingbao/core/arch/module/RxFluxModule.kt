package com.huyingbao.core.arch.module

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.huyingbao.core.arch.action.FlowActionManager
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
 * 3.提供[RxActionManager]
 *
 * 4.提供[RxDispatcher]
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Module
abstract class RxFluxModule {
    /**
     * 提供 [Application]
     */
    @Singleton
    @Binds
    abstract fun bindApplication(application: Application): Context

    /**
     * 提供 [ViewModelProvider.Factory]实现类[RxStoreFactory]
     *
     * 当需要注入[ViewModelProvider.Factory]对象时，提供[RxStoreFactory]实例对象
     */
    @Singleton
    @Binds
    abstract fun bindViewModelFactory(rxStoreFactory: RxStoreFactory): ViewModelProvider.Factory

    /**
     * 伴生对象中提供静态方法
     */
    @Module
    companion object {
        /**
         * 提供[RxActionManager]
         */
        @JvmStatic
        @Singleton
        @Provides
        fun provideRxActionManager(): RxActionManager {
            return RxActionManager()
        }

        /**
         * 提供[FlowActionManager]
         */
        @JvmStatic
        @Singleton
        @Provides
        fun provideFlowActionManager(): FlowActionManager {
            return FlowActionManager()
        }

        /**
         * 提供[RxDispatcher]
         */
        @JvmStatic
        @Singleton
        @Provides
        fun provideRxDispatcher(): RxDispatcher {
            return RxDispatcher()
        }
    }
}
