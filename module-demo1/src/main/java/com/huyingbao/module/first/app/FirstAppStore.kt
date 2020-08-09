package com.huyingbao.module.first.app

import com.huyingbao.core.annotations.AppLifecycleObserver
import com.huyingbao.core.arch.store.RxAppStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@AppLifecycleObserver
class FirstAppStore @Inject constructor() : RxAppStore()
