package com.huyingbao.core.arch.model;

import androidx.annotation.NonNull;

public class RxEvent {
    protected final String mTag;

    protected RxEvent(@NonNull String tag) {
        this.mTag = tag;
    }

    public String getTag() {
        return mTag;
    }
}
