package com.huyingbao.module.github.ui.person.store

import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.store.RxActivityStore
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 用户模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
@Singleton
class PersonStore @Inject constructor(rxDispatcher: RxDispatcher) : RxActivityStore(rxDispatcher) {
}
