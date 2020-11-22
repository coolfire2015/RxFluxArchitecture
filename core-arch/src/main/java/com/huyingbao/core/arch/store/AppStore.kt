package com.huyingbao.core.arch.store

import com.huyingbao.core.arch.dispatcher.Dispatcher


/**
 * 存储和管理Application生命周期内的数据
 *
 * Created by liujunfeng on 2019/1/1.
 */
abstract class AppStore(
        dispatcher: Dispatcher
) : StoreImpl(dispatcher) {}
