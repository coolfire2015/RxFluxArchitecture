package com.huyingbao.core.arch.view;

import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.model.RxError;
import com.huyingbao.core.arch.store.RxActionDispatch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public interface RxFluxView<T extends RxActionDispatch> {
    @Nullable
    T getRxStore();

    void onRxChanged(@NonNull RxChange rxChange);

    void onRxError(@NonNull RxError error);
}
