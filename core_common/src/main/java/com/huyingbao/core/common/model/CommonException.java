package com.huyingbao.core.common.model;


import java.io.IOException;

import androidx.annotation.Nullable;

/**
 * 自定义Exception
 * <p>
 * Created by liujunfeng on 2019/1/1.
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
