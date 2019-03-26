package com.huyingbao.core.arch.model;

import androidx.annotation.NonNull;

/**
 * @author liujunfeng
 * @date 2019/1/1
 */
public class RxChange extends RxEvent {
    private RxChange(@NonNull String tag) {
        super(tag);
    }

    public static RxChange newInstance(@NonNull RxEvent rxEvent) {
        return new RxChange(rxEvent.getTag());
    }
}
