package com.huyingbao.module.wan.kotlin


import com.huyingbao.core.annotations.RxAppBody
import com.huyingbao.core.base.BaseApp
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import org.greenrobot.eventbus.EventBus

/**
 * Created by liujunfeng on 2019/1/1.
 */
@RxAppBody
class WanApplication : BaseApp() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerWanComponent.builder().application(this).build()
    }
}
