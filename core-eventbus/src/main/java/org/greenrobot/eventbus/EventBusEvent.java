package org.greenrobot.eventbus;

import androidx.annotation.NonNull;

/**
 * 用于EventBus传递事件,提供事件标志tag
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public abstract class EventBusEvent {
    /**
     * 事件标志tag
     */
    protected final String tag;
    /**
     * 是否被全局捕获处理，默认true：全局捕获处理。
     * <p>
     * true:{@link EventBus#post(Object)}和{@link EventBus#postSticky(Object)}，使用统一tag："defaultTag"。
     * <p>
     * false:{@link EventBus#post(Object)}和{@link EventBus#postSticky(Object)}，使用自身{@link EventBusEvent#tag}。
     */
    private boolean isGlobalCatch = true;

    protected EventBusEvent(@NonNull String tag) {
        this.tag = tag;
    }


    /**
     * @return
     */
    public String getTag() {
        return tag;
    }

    public boolean isGlobalCatch() {
        return isGlobalCatch;
    }

    /**
     * @param globalCatch
     * @
     */
    public void setGlobalCatch(boolean globalCatch) {
        isGlobalCatch = globalCatch;
    }
}