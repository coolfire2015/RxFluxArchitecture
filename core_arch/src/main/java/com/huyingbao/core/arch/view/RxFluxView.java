package com.huyingbao.core.arch.view;

import com.huyingbao.core.arch.store.RxStore;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public interface RxFluxView {
    @Nullable
    List<RxStore> getLifecycleRxStoreList();
}
