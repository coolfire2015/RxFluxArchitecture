package com.huyingbao.module.github.ui.star.store

import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.store.RxActivityStore
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 点赞模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
@Singleton
class StarStore @Inject constructor(rxDispatcher: RxDispatcher) : RxActivityStore(rxDispatcher) {
}
