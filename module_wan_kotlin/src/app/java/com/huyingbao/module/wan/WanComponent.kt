package com.huyingbao.module.wan

import android.app.Application
import com.huyingbao.core.common.module.CommonModule
import com.huyingbao.module.wan.module.WanModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
@Component(modules = [
    WanModule::class,
    CommonModule::class,
    AndroidSupportInjectionModule::class
])
interface WanComponent : AndroidInjector<WanApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): WanComponent.Builder

        fun build(): WanComponent
    }
}
