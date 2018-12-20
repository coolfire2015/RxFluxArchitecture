package com.huyingbao.core.arch.store;

import com.huyingbao.core.arch.action.RxAction;

public class RxStoreChange {
    private String mStoreId;
    private RxAction mRxAction;

    public RxStoreChange(String storeId, RxAction rxAction) {
        this.mStoreId = storeId;
        this.mRxAction = rxAction;
    }

    public RxAction getRxAction() {
        return mRxAction;
    }

    public String getStoreId() {
        return mStoreId;
    }

    public String getType() {
        return mRxAction.getType();
    }
}
