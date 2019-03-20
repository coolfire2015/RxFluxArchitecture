package com.huyingbao.core.arch.model;

import androidx.annotation.NonNull;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public class RxLoading extends RxEvent {
    private final boolean mIsLoading;

    private RxLoading(@NonNull String tag, boolean isLoading) {
        super(tag);
        mIsLoading = isLoading;
    }

    public static RxLoading newRxLoading(@NonNull String tag, boolean isLoading) {
        return new RxLoading(tag, isLoading);
    }

    public boolean isLoading() {
        return mIsLoading;
    }
}
