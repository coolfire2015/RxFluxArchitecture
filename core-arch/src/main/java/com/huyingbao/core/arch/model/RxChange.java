package com.huyingbao.core.arch.model;

import androidx.annotation.NonNull;

import org.greenrobot.eventbus.EventBusEvent;

/**
 * UI响应通知，发送到{@link com.huyingbao.core.arch.view.RxFluxView}
 * <p>
 * 1.{@link com.huyingbao.core.arch.store.RxStore#postChange(RxChange)}
 * <p>
 * 2.{@link com.huyingbao.core.arch.action.RxActionCreator#postLocalChange(String)}
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public class RxChange extends EventBusEvent {
    private RxChange(@NonNull String tag) {
        super(tag);
    }

    public static RxChange newInstance(@NonNull String tag) {
        return new RxChange(tag);
    }
}
