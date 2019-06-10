package com.huyingbao.module.github.ui.code.store

import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.store.RxActivityStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CodeStore @Inject constructor(rxDispatcher: RxDispatcher?) : RxActivityStore(rxDispatcher) {
}
