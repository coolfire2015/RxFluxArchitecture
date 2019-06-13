package com.huyingbao.module.github.ui.issue.action

import com.huyingbao.core.arch.action.RxActionCreator
import com.huyingbao.core.arch.action.RxActionManager
import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.scope.ActivityScope
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * 问题模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
@ActivityScope
class IssueActionCreator @Inject constructor(
        rxDispatcher: RxDispatcher,
        rxActionManager: RxActionManager,
        private val retrofit: Retrofit
) : RxActionCreator(rxDispatcher, rxActionManager), IssueAction {
}
