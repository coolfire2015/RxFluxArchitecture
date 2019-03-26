package com.huyingbao.core.arch.model;

import androidx.annotation.NonNull;

/**
 * @author liujunfeng
 * @date 2019/1/1
 */
public class RxError extends RxEvent {
    private final Throwable mThrowable;

    private RxError(@NonNull String tag, Throwable throwable) {
        super(tag);
        mThrowable = throwable;
    }

    public static RxError newInstance(@NonNull RxEvent rxEvent, Throwable throwable) {
        return new RxError(rxEvent.getTag(), throwable);
    }

    public Throwable getThrowable() {
        return mThrowable;
    }
}
