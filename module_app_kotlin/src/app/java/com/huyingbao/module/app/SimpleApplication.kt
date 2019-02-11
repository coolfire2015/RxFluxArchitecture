package com.huyingbao.module.app


import com.huyingbao.core.common.CommonApp

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * 主Application
 * Created by liujunfeng on 2019/1/1.
 */
class SimpleApplication : CommonApp() {
    @Override
    protected fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerSimpleComponent.builder().application(this).build()
    }
}
