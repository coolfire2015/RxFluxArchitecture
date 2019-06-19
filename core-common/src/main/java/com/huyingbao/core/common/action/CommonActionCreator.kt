package com.huyingbao.core.common.action

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher

import javax.inject.Inject
import javax.inject.Singleton

/**
 * 公用的ActionCreator，主要使用其[RxActionCreator.postLocalChange]等方法。
 *
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
class CommonActionCreator @Inject
constructor(rxDispatcher: RxDispatcher, rxActionManager: RxActionManager) : RxActionCreator(rxDispatcher, rxActionManager)
