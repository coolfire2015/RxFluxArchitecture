package com.huyingbao.core.arch.model;

import androidx.annotation.NonNull;

/**
 * ActionCreator直接发送异常事件,通知View响应,不经过RxStore
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public class RxError extends BaseEvent {
    private final Throwable mThrowable;

    private RxError(@NonNull String tag, Throwable throwable) {
        super(tag);
        mThrowable = throwable;
    }

    public static RxError newInstance(@NonNull String tag, Throwable throwable) {
        return new RxError(tag, throwable);
    }

    public Throwable getThrowable() {
        return mThrowable;
    }
}
