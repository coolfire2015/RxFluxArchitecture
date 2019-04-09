package com.huyingbao.core.arch.model;

import androidx.annotation.NonNull;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public class RxEvent {
    protected final String mTag;

    protected RxEvent(@NonNull String tag) {
        this.mTag = tag;
    }

    public String getTag() {
        return mTag;
    }
}
