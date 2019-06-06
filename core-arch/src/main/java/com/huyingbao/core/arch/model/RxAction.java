package com.huyingbao.core.arch.model;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;

import org.greenrobot.eventbus.EventBusEvent;

import java.util.Objects;

/**
 * 操作结果通知，封装操作返回数据，
 * 由{@link com.huyingbao.core.arch.action.RxActionCreator}发送到{@link com.huyingbao.core.arch.store.RxStore}，
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public class RxAction extends EventBusEvent {
    private static final String RESPONSE = "response";
    private final ArrayMap<String, Object> mData;

    private RxAction(@NonNull String tag, ArrayMap<String, Object> data) {
        super(tag);
        this.mData = data;
    }

    public ArrayMap<String, Object> getData() {
        return mData;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String tag) {
        return (T) mData.get(tag);
    }

    @SuppressWarnings("unchecked")
    public <T> T getResponse() {
        return get(RESPONSE);
    }

    @SuppressWarnings("unchecked")
    public <T> void setResponse(T response) {
        getData().put(RESPONSE, response);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RxAction)) {
            return false;
        }
        RxAction rxAction = (RxAction) obj;
        if (!mTag.equals(rxAction.mTag)) {
            return false;
        }
        return Objects.equals(mData, rxAction.mData);
    }

    @Override
    public int hashCode() {
        int result = mTag.hashCode();
        result = 31 * result + (mData != null ? mData.hashCode() : 0);
        return result;
    }

    public static class Builder {
        private String mTag;
        private ArrayMap<String, Object> mData;

        public Builder(@NonNull String tag) {
            this.mTag = tag;
            this.mData = new ArrayMap<>();
        }

        public Builder put(@NonNull String key, Object value) {
            if (value != null) {
                mData.put(key, value);
            }
            return this;
        }

        public RxAction build() {
            return new RxAction(mTag, mData);
        }
    }
}