package com.huyingbao.module.gan.ui.random.view;

import android.os.Bundle;

import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.common.view.CommonRxActivity;
import com.huyingbao.module.gan.ui.random.action.RandomAction;
import com.huyingbao.module.gan.ui.random.store.RandomStore;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import dagger.Lazy;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public class RandomActivity extends CommonRxActivity<RandomStore> {
    @Inject
    Lazy<CategoryListFragment> mCategoryListFragmentLazy;
    @Inject
    Lazy<ProductFragment> mProductListFragmentLazy;

    @Override
    protected Fragment createFragment() {
        return mCategoryListFragmentLazy.get();
    }

    @Nullable
    @Override
    public RandomStore getRxStore() {
        return ViewModelProviders.of(this, mViewModelFactory).get(RandomStore.class);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
    }

    @Override
    @Subscribe(sticky = true)
    public void onRxChanged(@NonNull RxChange rxChange) {
        super.onRxChanged(rxChange);
        switch (rxChange.getTag()) {
            case RandomAction.TO_SHOW_DATA:
                addFragmentHideExisting(mProductListFragmentLazy.get());
                break;
        }
    }
}
