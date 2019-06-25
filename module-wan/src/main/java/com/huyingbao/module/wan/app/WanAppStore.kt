package com.huyingbao.module.wan.app

import android.app.Application

import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.store.RxAppStore

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WanAppStore @Inject constructor(
        application: Application,
        rxDispatcher: RxDispatcher
) : RxAppStore(application, rxDispatcher)
