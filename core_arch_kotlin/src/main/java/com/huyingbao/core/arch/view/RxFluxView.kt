package com.huyingbao.core.arch.view

import com.huyingbao.core.arch.model.RxChange
import com.huyingbao.core.arch.model.RxError
import androidx.lifecycle.ViewModel

/**
 * Created by liujunfeng on 2019/1/1.
 */
interface RxFluxView<T : ViewModel> {
    val rxStore: T?

    fun onRxChanged(rxChange: RxChange)

    fun onRxError(error: RxError)
}
