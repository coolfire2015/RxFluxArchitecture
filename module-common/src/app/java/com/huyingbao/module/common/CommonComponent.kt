package com.huyingbao.module.common

import android.app.Application
import com.huyingbao.module.common.app.CommonModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
@Component(modules = [CommonModule::class])
interface CommonComponent : AndroidInjector<CommonApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): CommonComponent
    }
}
