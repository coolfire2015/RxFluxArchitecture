package com.huyingbao.module.common

import android.app.Application
import com.huyingbao.core.common.module.CommonModule
import com.huyingbao.module.common.module.LoadingAppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
@Component(modules = [
    LoadingAppModule::class,
    CommonModule::class,
    AndroidInjectionModule::class
])
interface LoadingComponent : AndroidInjector<LoadingApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): LoadingComponent
    }
}
