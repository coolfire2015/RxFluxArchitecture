package com.huyingbao.module.app


import com.huyingbao.core.annotations.RxAppOwner
import com.huyingbao.core.base.BaseApp

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * 主Application
 *
 * Created by liujunfeng on 2019/1/1.
 */
@RxAppOwner
class SimpleApplication : BaseApp() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerSimpleComponent.builder().application(this).build()
    }
}
