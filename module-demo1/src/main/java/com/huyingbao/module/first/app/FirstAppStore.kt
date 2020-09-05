package com.huyingbao.module.first.app

import com.huyingbao.core.annotations.AppObserver
import com.huyingbao.core.arch.dispatcher.Dispatcher
import com.huyingbao.core.arch.store.AppStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@AppObserver
class FirstAppStore @Inject constructor(
        private val dispatcher: Dispatcher
) : AppStore(dispatcher)
