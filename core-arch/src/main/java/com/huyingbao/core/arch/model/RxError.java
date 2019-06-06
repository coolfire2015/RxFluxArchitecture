package com.huyingbao.core.arch.model;

import androidx.annotation.NonNull;

import org.greenrobot.eventbus.EventBusEvent;

/**
 * 操作异常通知，发送到{@link com.huyingbao.core.arch.view.RxFluxView}
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public class RxError extends EventBusEvent {
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
