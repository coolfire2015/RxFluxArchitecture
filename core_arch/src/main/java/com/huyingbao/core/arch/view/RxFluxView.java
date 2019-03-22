package com.huyingbao.core.arch.view;

import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.model.RxError;
import com.huyingbao.core.arch.model.RxLoading;
import com.huyingbao.core.arch.model.RxRetry;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

/**
 * 所有该接口的实现类，
 * 交由 {@link com.huyingbao.core.arch.dispatcher.RxDispatcher}
 * 来注册订阅，取消订阅管理。
 * Created by liujunfeng on 2019/1/1.
 */
public interface RxFluxView<T extends ViewModel> {
    /**
     * 1：{@link com.huyingbao.core.arch.lifecycle.RxActivityLifecycleObserver}
     * 与{@link com.huyingbao.core.arch.lifecycle.RxFragmentLifecycleObserver}
     * 调用该方法，将获取的RxStore(ViewModel)绑定View的Lifecycle。
     * <p>
     * 2：为实现类提供RxStore
     *
     * @return
     */
    @Nullable
    T getRxStore();

    /**
     * 注册订阅之后，接收RxStore发送的RxChange事件，
     * 响应操作正常结果。
     *
     * @param rxChange
     */
    void onRxChanged(@NonNull RxChange rxChange);

    /**
     * 注册订阅之后，接收RxActionCreator发送的RxError事件，
     * 响应操作异常结果。
     *
     * @param rxError
     */
    void onRxError(@NonNull RxError rxError);

    /**
     * 注册订阅之后，接收RxActionCreator发送的RxRetry事件，
     * 响应操作异常，重试操作。
     *
     * @param rxRetry
     */
    void onRxRetry(@NonNull RxRetry rxRetry);

    /**
     * 注册订阅之后，接收RxActionCreator发送的RxLoading事件，
     * 响应操作进度。
     *
     * @param rxLoading
     */
    void onRxLoading(@NonNull RxLoading rxLoading);
}
