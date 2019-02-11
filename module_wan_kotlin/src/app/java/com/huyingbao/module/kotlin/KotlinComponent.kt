package com.huyingbao.module.kotlin

import android.app.Application
import com.huyingbao.core.common.module.CommonModule
import com.huyingbao.module.kotlin.module.KotlinModule
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
    KotlinModule::class,
    CommonModule::class,
    AndroidSupportInjectionModule::class])
interface KotlinComponent : AndroidInjector<KotlinApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): KotlinComponent.Builder

        fun build(): KotlinComponent
    }
}
