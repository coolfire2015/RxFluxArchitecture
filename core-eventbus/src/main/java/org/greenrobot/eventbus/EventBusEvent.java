package org.greenrobot.eventbus;

import androidx.annotation.NonNull;

/**
 * 用于EventBus传递事件,提供事件标志tag
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class EventBusEvent {
    protected final String mTag;

    protected EventBusEvent(@NonNull String tag) {
        this.mTag = tag;
    }

    public String getTag() {
        return mTag;
    }
}