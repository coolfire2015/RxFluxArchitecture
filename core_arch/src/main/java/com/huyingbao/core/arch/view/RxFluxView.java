package com.huyingbao.core.arch.view;

import com.huyingbao.core.arch.action.RxActionError;
import com.huyingbao.core.arch.store.RxStore;
import com.huyingbao.core.arch.store.RxStoreChange;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public interface RxFluxView {
    void onRxStoreChanged(@NonNull RxStoreChange change);

    void onRxError(@NonNull RxActionError error);

    @Nullable
    List<RxStore> getLifecycleRxStoreList();
}
