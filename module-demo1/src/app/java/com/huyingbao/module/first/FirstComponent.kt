package com.huyingbao.module.first

import android.app.Application
import com.huyingbao.module.first.app.FirstAppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
@Component(modules = [FirstAppModule::class])
interface FirstComponent : AndroidInjector<FirstApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): FirstComponent
    }
}
