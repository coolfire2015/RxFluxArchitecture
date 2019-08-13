package com.huyingbao.module.second

import android.app.Application
import com.huyingbao.core.common.module.CommonModule
import com.huyingbao.module.second.module.SecondAppModule
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
    SecondAppModule::class,
    CommonModule::class,
    AndroidInjectionModule::class
])
interface SecondComponent : AndroidInjector<SecondApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): SecondComponent
    }
}
