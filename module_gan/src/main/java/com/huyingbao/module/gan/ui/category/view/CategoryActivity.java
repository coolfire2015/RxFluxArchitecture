package com.huyingbao.module.gan.ui.category.view;

import android.os.Bundle;

import com.huyingbao.core.arch.store.RxStore;
import com.huyingbao.core.arch.store.RxStoreChange;
import com.huyingbao.core.common.util.ActivityUtils;
import com.huyingbao.core.common.view.CommonActivity;
import com.huyingbao.module.gan.R;
import com.huyingbao.module.gan.ui.category.store.CategoryStore;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
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
