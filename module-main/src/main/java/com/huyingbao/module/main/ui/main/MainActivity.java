package com.huyingbao.module.main.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.huyingbao.core.store.RxStore;
import com.huyingbao.core.store.RxStoreChange;
import com.huyingbao.core.util.ActivityUtils;
import com.huyingbao.module.main.MainModuleActivity;
import com.huyingbao.module.main.R;
import com.huyingbao.module.main.action.MainActions;
import com.huyingbao.module.main.ui.main.module.MainStore;
import com.huyingbao.module.main.ui.shop.ShopActivity;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public class MainActivity extends MainModuleActivity {
    @Inject
    Lazy<MainFragment> mMainFragmentLazy;
    @Inject
    Lazy<ProductFragment> mProductListFragmentLazy;
    @Inject
    Lazy<ShopFragment> mShopFragmentLazy;

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        if (getSupportFragmentManager().findFragmentById(R.id.fl_content) == null)
            ActivityUtils.addFragment(getSupportFragmentManager(), mMainFragmentLazy.get(), R.id.fl_content);
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getType()) {
            case MainActions.TO_GITHUB:
                startActivity(ShopActivity.newIntent(mContext));
                break;
            case MainActions.TO_PRODUCT_LIST:
                ActivityUtils.addAndHideFragment(getSupportFragmentManager(), mProductListFragmentLazy.get(), R.id.fl_content);
                break;
            case MainActions.TO_SHOP:
                ActivityUtils.addAndHideFragment(getSupportFragmentManager(), mShopFragmentLazy.get(), R.id.fl_content);
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getLifecycleRxStoreList() {
        return Collections.singletonList(ViewModelProviders.of(this, mViewModelFactory).get(MainStore.class));
    }
}
