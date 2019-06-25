package com.huyingbao.module.wan

import com.huyingbao.core.annotations.RxAppOwner
import com.huyingbao.core.base.BaseApp

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * Created by liujunfeng on 2019/1/1.
 */
@RxAppOwner
class WanApplication : BaseApp() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerWanComponent.builder().application(this).build()
    }
}
