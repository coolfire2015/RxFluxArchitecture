package com.huyingbao.module.gan.ui.category.view;

import android.os.Bundle;

import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.common.view.CommonFxActivity;
import com.huyingbao.module.gan.ui.category.action.CategoryAction;
import com.huyingbao.module.gan.ui.category.store.CategoryStore;
import com.huyingbao.module.gan.ui.random.view.RandomFxActivity;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import dagger.Lazy;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public class CategoryFxActivity extends CommonFxActivity<CategoryStore> {
    @Inject
    Lazy<CategoryListFragment> mCategoryListFragmentLazy;

    @Nullable
    @Override
    public CategoryStore getRxStore() {
        return ViewModelProviders.of(this, mViewModelFactory).get(CategoryStore.class);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
    }

    @Override
    protected Fragment createFragment() {
        return mCategoryListFragmentLazy.get();
    }

    /**
     * 跳转随机列表页面，传送类别
     */
    @Subscribe(tags = {@Tag(CategoryAction.TO_RANDOM_LIST)})
    public void toRandomList(RxChange rxChange) {
        startActivity(RandomFxActivity.newIntent(this, getRxStore().getCategory()));
    }
}
