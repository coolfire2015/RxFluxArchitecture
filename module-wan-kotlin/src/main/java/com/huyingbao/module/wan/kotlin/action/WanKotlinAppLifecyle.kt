package com.huyingbao.module.wan.kotlin.action

import android.app.Application

import com.huyingbao.core.annotations.RxAppDelegate
import com.huyingbao.core.arch.RxAppLifecycle
import com.huyingbao.module.wan.kotlin.WanEventBusIndex

import org.greenrobot.eventbus.EventBus

@RxAppDelegate
class WanKotlinAppLifecyle : RxAppLifecycle {
    override fun onCreate(application: Application) {
        EventBus.builder()
                .addIndex(WanEventBusIndex())
                .eventInheritance(false)
    }
}
