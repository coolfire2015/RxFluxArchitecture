package com.huyingbao.module.gan.ui.main.action

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.scope.ActivityScope

import javax.inject.Inject


/**
 * rxAction创建发送管理类
 * Created by liujunfeng on 2019/1/1.
 */
@ActivityScope
class MainActionCreator @Inject
internal constructor(rxDispatcher: RxDispatcher, rxActionManager: RxActionManager) : RxActionCreator(rxDispatcher, rxActionManager), MainAction
