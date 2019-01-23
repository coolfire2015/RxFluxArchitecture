package com.huyingbao.module.kotlin

import com.huyingbao.core.common.CommonApp

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * Created by liujunfeng on 2019/1/1.
 */
class KotlinApplication : CommonApp() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerKotlinComponent.builder().application(this).build()
    }
}
