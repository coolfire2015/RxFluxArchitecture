package com.huyingbao.module.github.ui.person.store

import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.store.RxActivityStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonStore @Inject constructor(rxDispatcher: RxDispatcher?) : RxActivityStore(rxDispatcher) {
}
