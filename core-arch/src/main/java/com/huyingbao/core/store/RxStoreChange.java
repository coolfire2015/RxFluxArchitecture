package com.huyingbao.core.store;

import com.huyingbao.core.action.RxAction;

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
