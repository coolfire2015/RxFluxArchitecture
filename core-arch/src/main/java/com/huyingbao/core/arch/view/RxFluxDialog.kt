package com.huyingbao.core.arch.view

import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.ViewModel

/**
 * 实现[com.huyingbao.core.arch.view.RxFluxView]，
 * 持有依赖注入的[com.huyingbao.core.arch.store.RxStore]对象，
 * 并绑定到自身生命周期。
 *
 * 实现[com.huyingbao.core.arch.view.RxSubscriberView]，
 * 根据自身生命周期，自动注册订阅或者取消订阅。
 *
 * Created by liujunfeng on 2019/1/1.
 */
abstract class RxFluxDialog<T : ViewModel> :
        AppCompatDialogFragment(),
        RxFluxView<T>,
        RxSubscriberView {
}