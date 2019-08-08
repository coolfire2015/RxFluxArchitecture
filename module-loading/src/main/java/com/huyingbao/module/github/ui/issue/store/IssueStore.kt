package com.huyingbao.module.github.ui.issue.store

import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.store.RxActivityStore
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 问题模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
@Singleton
class IssueStore @Inject constructor(rxDispatcher: RxDispatcher) : RxActivityStore(rxDispatcher) {
}
