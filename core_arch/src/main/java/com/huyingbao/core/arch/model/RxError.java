package com.huyingbao.core.arch.model;

import androidx.annotation.NonNull;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public class RxError extends RxEvent {
    private final Throwable mThrowable;

    private RxError(@NonNull String tag, Throwable throwable) {
        super(tag);
        mThrowable = throwable;
    }

    public static RxError newRxError(@NonNull String tag, Throwable throwable) {
        return new RxError(tag, throwable);
    }

    public Throwable getThrowable() {
        return mThrowable;
    }
}
