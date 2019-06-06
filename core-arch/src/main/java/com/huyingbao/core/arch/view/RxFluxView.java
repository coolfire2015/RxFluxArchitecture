package com.huyingbao.core.arch.view;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

/**
 * 所有该接口的实现类(Activity/Fragment)，持有跟随自身生命周期的Store。
 * <p>
 * View在destroy时,调用Store的onCleared()方法清理数据并不再持有Store对象。
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public interface RxFluxView<T extends ViewModel> {
    /**
     * 为实现类提供Store
     */
    @Nullable
    T getRxStore();
}
