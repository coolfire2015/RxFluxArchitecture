package com.huyingbao.core.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.huyingbao.core.action.RxError;
import com.huyingbao.core.store.RxStore;
import com.huyingbao.core.store.RxStoreChange;

import java.util.List;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public interface RxViewDispatch {
    void onRxStoreChanged(@NonNull RxStoreChange change);

    void onRxError(@NonNull RxError error);

    @Nullable
    List<RxStore> getLifecycleRxStoreList();
}
