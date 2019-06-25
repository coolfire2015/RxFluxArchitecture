package com.huyingbao.module.wan

import android.app.Application
import com.huyingbao.core.common.module.CommonModule
import com.huyingbao.module.wan.module.WanAppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
@Component(modules = [WanAppModule::class, CommonModule::class, AndroidInjectionModule::class])
interface WanComponent : AndroidInjector<WanApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): WanComponent.Builder

        fun build(): WanComponent
    }
}
