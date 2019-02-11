package com.huyingbao.module.gan

import com.huyingbao.core.common.CommonApp

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * Created by liujunfeng on 2019/1/1.
 */
class GanApplication : CommonApp() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerGanComponent.builder().application(this).build()
    }
}
