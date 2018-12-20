package com.huyingbao.module.gan.ui.category.view;

import android.os.Bundle;

import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.store.RxStore;
import com.huyingbao.core.common.util.ActivityUtils;
import com.huyingbao.core.common.view.CommonActivity;
import com.huyingbao.module.gan.R;
import com.huyingbao.module.gan.ui.category.action.CategoryAction;
import com.huyingbao.module.gan.ui.category.store.CategoryStore;
import com.huyingbao.module.gan.ui.random.view.RandomActivity;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import dagger.Lazy;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public class CategoryActivity extends CommonActivity {
    @Inject
    Lazy<CategoryListFragment> mCategoryListFragmentLazy;

    private CategoryStore mStore;

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mStore = ViewModelProviders.of(this, mViewModelFactory).get(CategoryStore.class);
        ActivityUtils.addFragment(getSupportFragmentManager(),
                mCategoryListFragmentLazy.get(),
                R.id.fl_content);
    }

    @Nullable
    @Override
    public List<RxStore> getLifecycleRxStoreList() {
        return Collections.singletonList(mStore);
    }

    /**
     * 跳转随机列表页面，传送类别
     */
    @Subscribe(tags = {@Tag(CategoryAction.TO_RANDOM_LIST)})
    public void toRandomList(RxChange rxChange) {
        startActivity(RandomActivity.newIntent(this, mStore.getCategory()));
    }
}
