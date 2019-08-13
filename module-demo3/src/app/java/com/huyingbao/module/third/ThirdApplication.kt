package com.huyingbao.module.third

import com.huyingbao.core.annotations.RxAppOwner
import com.huyingbao.core.base.BaseApp
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * Created by liujunfeng on 2019/1/1.
 */
@RxAppOwner
class ThirdApplication : BaseApp() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerThirdComponent.builder().application(this).build()
    }
}
