package com.huyingbao.core.common.model;


import java.io.IOException;

/**
 * 自定义HttpException
 * Created by liujunfeng on 2017/12/7.
 */
public class CommonHttpException extends IOException {
    private final int mCode;
    private final String mMessage;

    public CommonHttpException(int code, String message) {
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
