package com.huyingbao.module.second.app

import androidx.hilt.lifecycle.ViewModelInject
import com.huyingbao.core.arch.store.RxAppStore
import javax.inject.Singleton

@Singleton
class SecondAppStore @ViewModelInject constructor() : RxAppStore()
