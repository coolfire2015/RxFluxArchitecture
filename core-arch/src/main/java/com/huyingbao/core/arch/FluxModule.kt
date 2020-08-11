package com.huyingbao.core.arch

import com.huyingbao.core.arch.action.ActionManager
import com.huyingbao.core.arch.dispatcher.Dispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


/**
 * 依赖注入仓库
 *
 * 1.提供[ActionManager]
 *
 * 2.提供[Dispatcher]
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Module
@InstallIn(ApplicationComponent::class)
class FluxModule {
    /**
     * 提供[ActionManager]
     */
    @Singleton
    @Provides
    fun provideActionManager(): ActionManager {
        return ActionManager()
    }

    /**
     * 提供[Dispatcher]
     */
    @Singleton
    @Provides
    fun provideDispatcher(): Dispatcher {
        return Dispatcher()
    }
}
