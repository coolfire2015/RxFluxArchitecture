package com.huyingbao.module.github.ui.code.action

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.module.github.BuildConfig
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

/**
 * 代码模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
@ActivityScope
class CodeActionCreator @Inject constructor(
        rxDispatcher: RxDispatcher,
        rxActionManager: RxActionManager,
        @Named(value = BuildConfig.MODULE_NAME) private val retrofit: Retrofit
) : RxActionCreator(rxDispatcher, rxActionManager), CodeAction {
}
