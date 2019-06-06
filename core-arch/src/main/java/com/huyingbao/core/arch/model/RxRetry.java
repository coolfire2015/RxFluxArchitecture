package com.huyingbao.core.arch.model;

import androidx.annotation.NonNull;

import org.greenrobot.eventbus.EventBusEvent;

import io.reactivex.Observable;

/**
 * 操作异常重试通知，发送到{@link com.huyingbao.core.arch.view.RxFluxView｝
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public class RxRetry<T> extends EventBusEvent {
    private final Throwable mThrowable;
    private final Observable<T> mObservable;

    private RxRetry(@NonNull String tag, Throwable throwable, Observable<T> observable) {
        super(tag);
        mThrowable = throwable;
        mObservable = observable;
    }

    public static <T> RxRetry newInstance(@NonNull String tag, Throwable throwable, Observable<T> httpObservable) {
        return new RxRetry(tag, throwable, httpObservable);
    }

    public Throwable getThrowable() {
        return mThrowable;
    }

    public Observable<T> getObservable() {
        return mObservable;
    }
}
