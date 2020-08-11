package com.huyingbao.core.arch.store

import com.huyingbao.core.arch.model.Change

/**
 * 所有Store都需要实现的接口：
 *
 * 1.存储和管理与UI相关的数据，负责数个逻辑相关[com.huyingbao.core.arch.view.FluxView]状态，对外提供get方法。
 *
 * 2.接收[com.huyingbao.core.arch.dispatcher.Dispatcher]发送的[com.huyingbao.core.arch.model.Action]。
 *
 * 3.发送[Change]给[com.huyingbao.core.arch.view.FluxView]。
 *
 * Created by liujunfeng on 2019/1/1.
 */
interface Store {
    /**
     * 注册当前[Store]，接收数据
     */
    fun subscribe()

    /**
     * 解除注册当前[Store]，接收数据
     */
    fun unsubscribe()

    /**
     * 发送[Change]给[com.huyingbao.core.arch.view.FluxView]
     */
    fun postChange(change: Change)
}
