package com.huyingbao.module.second

import android.app.Application
import com.huyingbao.module.second.app.SecondAppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
@Component(modules = [SecondAppModule::class])
interface SecondComponent : AndroidInjector<SecondApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): SecondComponent
    }
}
