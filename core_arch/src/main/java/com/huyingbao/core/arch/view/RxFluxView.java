package com.huyingbao.core.arch.view;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

/**
 * 所有该接口的实现类，
 * 交由 {@link com.huyingbao.core.arch.dispatcher.RxDispatcher}
 * 来注册订阅，取消订阅管理。
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public interface RxFluxView<T extends ViewModel> {
    /**
     * 1：{@link com.huyingbao.core.arch.lifecycle.RxActivityLifecycleObserver}
     * 与{@link com.huyingbao.core.arch.lifecycle.RxFragmentLifecycleObserver}
     * 调用该方法，将获取的RxStore(ViewModel)绑定View的Lifecycle。
     * <p>
     * 2：为实现类提供RxStore
     * <p>
     * 3: 获取ViewModelFactory(单例)中的RxStore(单例),并跟随当前View的生命周期
     * View在destroy时,RxStore调用onCleared()方法清理数据
     * View在destroy时,不再持有该Store对象
     *
     * @return
     */
    @Nullable
    T getRxStore();
}
