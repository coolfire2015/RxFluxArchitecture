package com.huyingbao.module.github.ui.search.store

import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.store.RxActivityStore
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 搜索模块
 *
 * Created by liujunfeng on 2019/6/10.
 */
@Singleton
class SearchStore @Inject constructor(rxDispatcher: RxDispatcher?) : RxActivityStore(rxDispatcher) {
}
