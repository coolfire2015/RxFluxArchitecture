package com.huyingbao.core.action;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public class RxActionError extends RxAction {

    private static final String ERROR_TYPE = "RxError_Type";

    private static final String ERROR_ACTION = "RxError_Action";

    private static final String ERROR_THROWABLE = "RxError_Throwable";

    private RxActionError(String type, ArrayMap<String, Object> data) {
        super(type, data);
    }

    public static RxActionError newRxError(@NonNull RxAction action, Throwable throwable) {
        ArrayMap<String, Object> data = new ArrayMap<>();
        data.put(ERROR_ACTION, action);
        data.put(ERROR_THROWABLE, throwable);
        return new RxActionError(ERROR_TYPE, data);
    }

    public RxAction getAction() {
        return (RxAction) getData().get(ERROR_ACTION);
    }

    public Throwable getThrowable() {
        return (Throwable) getData().get(ERROR_THROWABLE);
    }
}
