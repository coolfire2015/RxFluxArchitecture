package com.huyingbao.core.arch.model;

import androidx.annotation.NonNull;

/**
 * @author liujunfeng
 * @date 2019/1/1
 */
public class RxLoading extends RxEvent {
    /**
     * true：操作进行中，用于显示操作进度提示
     * false：操作结束，用于隐藏操作进度提示
     */
    private final boolean mIsLoading;

    private RxLoading(@NonNull String tag, boolean isLoading) {
        super(tag);
        mIsLoading = isLoading;
    }

    public static RxLoading newInstance(@NonNull RxEvent rxEvent, boolean isLoading) {
        return new RxLoading(rxEvent.getTag(), isLoading);
    }

    public boolean isLoading() {
        return mIsLoading;
    }
}
