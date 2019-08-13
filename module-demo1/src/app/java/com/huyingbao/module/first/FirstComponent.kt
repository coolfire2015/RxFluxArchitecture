package com.huyingbao.module.first

import android.app.Application
import com.huyingbao.core.common.module.CommonModule
import com.huyingbao.module.first.module.FirstAppModule
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
    FirstAppModule::class,
    CommonModule::class,
    AndroidInjectionModule::class
])
interface FirstComponent : AndroidInjector<FirstApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): FirstComponent
    }
}
