package com.huyingbao.module.gan.ui.category.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.huyingbao.core.store.RxStore;
import com.huyingbao.core.store.RxStoreChange;
import com.huyingbao.core.util.ActivityUtils;
import com.huyingbao.core.view.CommonActivity;
import com.huyingbao.module.gan.R;
import com.huyingbao.module.gan.ui.category.store.CategoryStore;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public class CategoryActivity extends CommonActivity {
    @Inject
    Lazy<CategoryListFragment> mCategoryListFragmentLazy;

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        ActivityUtils.addFragment(getSupportFragmentManager(),
                mCategoryListFragmentLazy.get(),
                R.id.fl_content);
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
    }

    @Nullable
    @Override
    public List<RxStore> getLifecycleRxStoreList() {
        CategoryStore randomStore = ViewModelProviders.of(this, mViewModelFactory).get(CategoryStore.class);
        return Collections.singletonList(randomStore);
    }
}
