package com.huyingbao.core.arch.model;

import androidx.annotation.NonNull;

/**
 * 抽象方法,用于EventBus传递事件,提供事件标志tag
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class BaseEvent {
    protected final String mTag;

    protected BaseEvent(@NonNull String tag) {
        this.mTag = tag;
    }

    public String getTag() {
        return mTag;
    }
}
