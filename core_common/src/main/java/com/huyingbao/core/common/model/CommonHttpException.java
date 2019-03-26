package com.huyingbao.core.common.model;


import java.io.IOException;

import androidx.annotation.Nullable;

/**
 * 自定义HttpException
 *
 * @author liujunfeng
 * @date 2019/1/1
 */
public class CommonHttpException extends IOException {
    private final int mCode;

    private final String mMessage;

    public CommonHttpException(int code, @Nullable String message) {
        super("HTTP " + code + " " + message);
        this.mCode = code;
        this.mMessage = message;
    }

    /**
     * HTTP status mCode.
     */
    public int code() {
        return mCode;
    }

    /**
     * HTTP status mMessage.
     */
    public String message() {
        return mMessage;
    }
}
