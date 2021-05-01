package com.huyingbao.core.arch.view

import com.huyingbao.core.arch.dispatcher.FluxSubscriber
import com.huyingbao.core.arch.store.FluxStore

/**
 * View层
 *
 * 持有跟随自身生命周期的[FluxStore]。
 *
 * 继承[FluxSubscriber]，接受总线事件，根据自身生命周期，自动注册订阅或者取消订阅

 * View在destroy时,调用[FluxStore]的onCleared()方法清理数据并不再持有[FluxStore]对象。
 *
 * Created by liujunfeng on 2019/1/1.
 */
interface FluxView : FluxSubscriber {
    /**
     * 为实现类提供Store
     */
    val store: FluxStore
}
