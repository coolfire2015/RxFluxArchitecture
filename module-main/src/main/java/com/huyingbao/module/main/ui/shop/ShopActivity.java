package com.huyingbao.module.main.ui.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.huyingbao.core.action.RxError;
import com.huyingbao.core.store.RxStore;
import com.huyingbao.core.store.RxStoreChange;
import com.huyingbao.module.main.MainModuleActivity;
import com.huyingbao.module.main.action.MainActionCreator;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public class ShopActivity extends MainModuleActivity {
    @Inject
    MainActionCreator mActionCreator;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ShopActivity.class);
        return intent;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mActionCreator.getProductList();
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
    }

    @Override
    public void onRxError(@NonNull RxError error) {

    }

    @Nullable
    @Override
    public List<RxStore> getLifecycleRxStoreList() {
        return null;
    }
}
