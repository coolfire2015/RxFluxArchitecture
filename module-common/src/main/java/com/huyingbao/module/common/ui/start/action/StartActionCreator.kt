package com.huyingbao.module.common.ui.start.action

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.scope.ActivityScope
import javax.inject.Inject

/**
 * 引导模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
@ActivityScope
class StartActionCreator @Inject constructor(
        rxDispatcher: RxDispatcher,
        rxActionManager: RxActionManager
) : RxActionCreator(rxDispatcher, rxActionManager), StartAction {
}