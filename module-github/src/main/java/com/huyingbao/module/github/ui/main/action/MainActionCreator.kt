package com.huyingbao.module.github.ui.main.action

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import retrofit2.Retrofit
import javax.inject.Inject

class MainActionCreator @Inject constructor(
        rxDispatcher: RxDispatcher,
        rxActionManager: RxActionManager,
        private val retrofit: Retrofit
) : RxActionCreator(rxDispatcher, rxActionManager), MainAction {
}