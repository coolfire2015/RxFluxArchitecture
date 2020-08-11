package com.huyingbao.core.arch.store

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.huyingbao.core.arch.dispatcher.Dispatcher


/**
 * 继承[ViewModel]，在屏幕旋转或配置更改引起的Activity重建时存活下来，其持有数据可以继续使用。
 *
 * 实现[LifecycleObserver]，关联Activity生命周期。
 *
 * Created by liujunfeng on 2019/1/1.
 */
abstract class ActivityStore(
        override var dispatcher: Dispatcher
) : StoreImpl() {}
