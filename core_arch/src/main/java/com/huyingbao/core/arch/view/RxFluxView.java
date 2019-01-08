package com.huyingbao.core.arch.view;

import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.model.RxError;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public interface RxFluxView<T extends ViewModel> {
    @Nullable
    T getRxStore();

    void onRxChanged(@NonNull RxChange rxChange);

    void onRxError(@NonNull RxError error);
}
