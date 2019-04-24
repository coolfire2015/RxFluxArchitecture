package com.huyingbao.core.arch.model;

import androidx.annotation.NonNull;
import io.reactivex.Observable;

/**
 * ActionCreator直接发送异常重试事件,通知View响应,不经过RxStore
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public class RxRetry<T> extends BaseEvent {
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
