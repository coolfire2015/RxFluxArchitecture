package com.huyingbao.module.third.ui.store

import androidx.hilt.lifecycle.ViewModelInject
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.store.RxActivityStore

class ThirdStore @ViewModelInject constructor(
        override var rxDispatcher: RxDispatcher
) : RxActivityStore(rxDispatcher) {
}
