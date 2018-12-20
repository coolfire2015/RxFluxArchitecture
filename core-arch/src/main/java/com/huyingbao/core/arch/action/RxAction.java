package com.huyingbao.core.arch.action;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;

/**
 * action封装类，用于传递数据，使用Builder创建
 * Created by liujunfeng on 2017/12/7.
 */
public class RxAction {
    private final String mType;
    private final ArrayMap<String, Object> mData;

    RxAction(@NonNull String type, ArrayMap<String, Object> data) {
        this.mType = type;
        this.mData = data;
    }

    public String getType() {
        return mType;
    }

    public ArrayMap<String, Object> getData() {
        return mData;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String tag) {
        return (T) mData.get(tag);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof RxAction)) return false;
        RxAction rxAction = (RxAction) obj;
        if (!mType.equals(rxAction.mType)) return false;
        return !(mData != null ? !mData.equals(rxAction.mData) : rxAction.mData != null);
    }

    @Override
    public int hashCode() {
        int result = mType.hashCode();
        result = 31 * result + (mData != null ? mData.hashCode() : 0);
        return result;
    }

    public static class Builder {
        private String mType;
        private ArrayMap<String, Object> mData;

        public Builder(@NonNull String type) {
            this.mType = type;
            this.mData = new ArrayMap<>();
        }

        public Builder put(@NonNull String key, Object value) {
            if (value != null) mData.put(key, value);
            return this;
        }

        public RxAction build() {
            return new RxAction(mType, mData);
        }
    }
}