package com.huyingbao.core.arch.view

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

/**
 * 实现[com.huyingbao.core.arch.view.FluxView]，
 * 持有依赖注入的[com.huyingbao.core.arch.store.Store]对象，
 * 并绑定到自身生命周期。
 *
 * 实现[com.huyingbao.core.arch.view.SubscriberView]，
 * 根据自身生命周期，自动注册订阅或者取消订阅。
 *
 * Created by liujunfeng on 2019/1/1.
 */
abstract class FluxFragment<T : ViewModel> :
        Fragment(),
        FluxView<T>,
        SubscriberView {
}