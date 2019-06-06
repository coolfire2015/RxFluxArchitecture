package com.huyingbao.core.arch.model;

import androidx.annotation.NonNull;

import org.greenrobot.eventbus.EventBusEvent;

/**
 * 操作进度通知，发送到{@link com.huyingbao.core.arch.view.RxFluxView}
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public class RxLoading extends EventBusEvent {
    /**
     * true：操作进行中，用于显示操作进度提示
     * false：操作结束，用于隐藏操作进度提示
     */
    private final boolean mIsLoading;

    private RxLoading(@NonNull String tag, boolean isLoading) {
        super(tag);
        mIsLoading = isLoading;
    }

    public static RxLoading newInstance(@NonNull String tag, boolean isLoading) {
        return new RxLoading(tag, isLoading);
    }

    public boolean isLoading() {
        return mIsLoading;
    }
}
