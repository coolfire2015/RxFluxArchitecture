package com.huyingbao.core.arch.store;

import com.huyingbao.core.arch.model.RxChange;

public interface RxStore {
    /**
     * 传递更改,传递一个RxStoreChange,
     * 每一个RxStoreChange由storeId和action组成
     *
     * @param change
     */
    void postChange(RxChange change);
}
