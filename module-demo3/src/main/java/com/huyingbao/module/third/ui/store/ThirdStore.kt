package com.huyingbao.module.third.ui.store

import androidx.hilt.lifecycle.ViewModelInject
import com.huyingbao.core.arch.dispatcher.Dispatcher
import com.huyingbao.core.arch.store.ActivityStore

class ThirdStore @ViewModelInject constructor(
        override var dispatcher: Dispatcher
) : ActivityStore(dispatcher) {
}
