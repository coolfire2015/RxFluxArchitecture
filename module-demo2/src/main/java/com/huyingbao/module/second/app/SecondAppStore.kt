package com.huyingbao.module.second.app

import androidx.hilt.lifecycle.ViewModelInject
import com.huyingbao.core.arch.store.AppStore
import javax.inject.Singleton

@Singleton
class SecondAppStore @ViewModelInject constructor() : AppStore()
