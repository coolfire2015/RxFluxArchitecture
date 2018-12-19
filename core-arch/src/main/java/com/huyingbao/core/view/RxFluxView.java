package com.huyingbao.core.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.huyingbao.core.action.RxActionError;
import com.huyingbao.core.store.RxStore;
import com.huyingbao.core.store.RxStoreChange;

import java.util.List;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public interface RxFluxView {
    void onRxStoreChanged(@NonNull RxStoreChange change);

    void onRxError(@NonNull RxActionError error);

    @Nullable
    List<RxStore> getLifecycleRxStoreList();
}
