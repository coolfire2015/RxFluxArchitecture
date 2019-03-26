package com.huyingbao.module.wan

import com.huyingbao.core.common.CommonApp

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * @author liujunfeng
 * @date 2019/1/1
 */
class WanApplication : CommonApp() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerWanComponent.builder().application(this).build()
    }
}
