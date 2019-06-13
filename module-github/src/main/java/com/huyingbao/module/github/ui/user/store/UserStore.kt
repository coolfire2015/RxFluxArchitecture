package com.huyingbao.module.github.ui.user.store

import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.store.RxActivityStore
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 当前登录用户模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
@Singleton
class UserStore @Inject constructor(rxDispatcher: RxDispatcher?) : RxActivityStore(rxDispatcher) {
}