package com.huyingbao.core.arch.view

import androidx.lifecycle.ViewModel

/**
 * 所有该接口的实现类(Activity/Fragment)，持有跟随自身生命周期的Store。
 *
 * View在destroy时,调用Store的onCleared()方法清理数据并不再持有Store对象。
 *
 * Created by liujunfeng on 2019/1/1.
 */
interface FluxView<T : ViewModel> {
    /**
     * 为实现类提供Store
     */
    val rxStore: T?
}
