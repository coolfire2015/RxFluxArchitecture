package com.huyingbao.core.arch.model;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public class RxError extends RxEvent {
    private final Throwable mThrowable;
    private final ArrayMap<String, Object> mData;

    private RxError(@NonNull String tag, Throwable throwable, ArrayMap<String, Object> data) {
        super(tag);
        mThrowable = throwable;
        mData = data;
    }

    public static RxError newRxError(@NonNull RxAction rxAction, Throwable throwable) {
        return new RxError(rxAction.getTag(), throwable, rxAction.getData());
    }

    public Throwable getThrowable() {
        return mThrowable;
    }

    public ArrayMap<String, Object> getData() {
        return mData;
    }
}
