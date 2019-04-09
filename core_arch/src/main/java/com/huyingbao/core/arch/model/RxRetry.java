package com.huyingbao.core.arch.model;

import androidx.annotation.NonNull;
import io.reactivex.Observable;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public class RxRetry<T> extends RxEvent {
    private final Throwable mThrowable;
    private final Observable<T> mObservable;

    private RxRetry(@NonNull String tag, Throwable throwable, Observable<T> observable) {
        super(tag);
        mThrowable = throwable;
        mObservable = observable;
    }

    public static <T> RxRetry newInstance(@NonNull RxEvent rxEvent, Throwable throwable, Observable<T> httpObservable) {
        return new RxRetry(rxEvent.getTag(), throwable, httpObservable);
    }

    public Throwable getThrowable() {
        return mThrowable;
    }

    public Observable<T> getObservable() {
        return mObservable;
    }
}
