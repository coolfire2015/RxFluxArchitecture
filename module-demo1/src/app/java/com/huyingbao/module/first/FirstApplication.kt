package com.huyingbao.module.first

import com.huyingbao.core.annotations.RxAppOwner
import com.huyingbao.core.base.BaseApp
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * Created by liujunfeng on 2019/1/1.
 */
@RxAppOwner
class FirstApplication : BaseApp() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerFirstComponent.builder().application(this).build()
    }
}
