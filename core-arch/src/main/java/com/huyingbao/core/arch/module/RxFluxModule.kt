package com.huyingbao.core.arch.module

import com.huyingbao.core.arch.action.FlowActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


/**
 * 依赖注入仓库
 *
 * 1.提供[FlowActionManager]
 *
 * 2.提供[RxDispatcher]
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Module
@InstallIn(ApplicationComponent::class)
class RxFluxModule {
    /**
     * 提供[FlowActionManager]
     */
    @Singleton
    @Provides
    fun provideFlowActionManager(): FlowActionManager {
        return FlowActionManager()
    }

    /**
     * 提供[RxDispatcher]
     */
    @Singleton
    @Provides
    fun provideRxDispatcher(): RxDispatcher {
        return RxDispatcher()
    }
}
