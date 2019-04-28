package com.huyingbao.core.common.model;


import androidx.annotation.Nullable;

import java.io.IOException;

/**
 * 自定义Exception
 * Created by liujunfeng on 2017/12/7.
 */
public class CommonException extends IOException {
    private final int mCode;

    private final String mMessage;

    public CommonException(int code, @Nullable String message) {
        super("Error Code " + code + " " + message);
        this.mCode = code;
        this.mMessage = message;
    }

    /**
     * Error Code.
     */
    public int code() {
        return mCode;
    }

    /**
     * Error Message.
     */
    public String message() {
        return mMessage;
    }
}
