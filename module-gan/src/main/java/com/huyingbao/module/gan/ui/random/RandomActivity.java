package com.huyingbao.module.gan.ui.random;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.huyingbao.core.common.CommonActivity;
import com.huyingbao.core.store.RxStore;
import com.huyingbao.core.store.RxStoreChange;
import com.huyingbao.core.util.ActivityUtils;
import com.huyingbao.module.gan.R;
import com.huyingbao.module.gan.ui.random.action.RandomActions;
import com.huyingbao.module.gan.ui.random.store.RandomStore;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public class RandomActivity extends CommonActivity {
    @Inject
    Lazy<CategoryListFragment> mCategoryListFragmentLazy;
    @Inject
    Lazy<RandomFragment> mRandomFragmentLazy;
    @Inject
    Lazy<ProductFragment> mProductListFragmentLazy;

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        if (getSupportFragmentManager().findFragmentById(R.id.fl_content) == null)
            ActivityUtils.addFragment(getSupportFragmentManager(), mCategoryListFragmentLazy.get(), R.id.fl_content);
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getType()) {
            case RandomActions.TO_GITHUB:
                ARouter.getInstance().build("/git/GitActivity").navigation();
                break;
            case RandomActions.TO_PRODUCT_LIST:
                ActivityUtils.addAndHideFragment(getSupportFragmentManager(), mProductListFragmentLazy.get(), R.id.fl_content);
                break;
            case RandomActions.TO_SHOP:
                break;
        }
    }

    @Nullable
    @Override
    public List<RxStore> getLifecycleRxStoreList() {
        return Collections.singletonList(ViewModelProviders.of(this, mViewModelFactory).get(RandomStore.class));
    }
}
