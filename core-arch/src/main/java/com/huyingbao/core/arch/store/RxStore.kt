package com.huyingbao.core.arch.store

import com.huyingbao.core.arch.model.RxChange

/**
 * 所有Store都需要实现的接口：
 *
 * 1.存储和管理与UI相关的数据，负责数个逻辑相关[com.huyingbao.core.arch.view.RxFluxView]状态，对外提供get方法。
 *
 * 2.接收[com.huyingbao.core.arch.dispatcher.RxDispatcher]发送的[com.huyingbao.core.arch.model.RxAction]。
 *
 * 3.发送[RxChange]给[com.huyingbao.core.arch.view.RxFluxView]。
 *
 * Created by liujunfeng on 2019/1/1.
 */
interface RxStore {
    /**
     * 注册当前[RxStore]，接收数据
     */
    fun subscribe()

    /**
     * 解除注册当前[RxStore]，接收数据
     */
    fun unsubscribe()

    /**
     * 发送[RxChange]给[com.huyingbao.core.arch.view.RxFluxView]
     */
    fun postChange(rxChange: RxChange)
}
