package com.huyingbao.module.third

import android.app.Application
import com.huyingbao.module.third.module.ThirdAppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
@Component(modules = [ThirdAppModule::class])
interface ThirdComponent : AndroidInjector<ThirdApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ThirdComponent
    }
}
