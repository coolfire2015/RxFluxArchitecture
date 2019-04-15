package com.huyingbao.module.gan.ui.random.view;

import android.os.Bundle;

import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.base.rxview.BaseRxActivity;
import com.huyingbao.module.gan.ui.random.action.RandomAction;
import com.huyingbao.module.gan.ui.random.store.RandomStore;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import dagger.Lazy;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public class RandomActivity extends BaseRxActivity<RandomStore> {
    @Inject
    Lazy<CategoryFragment> mCategoryFragmentLazy;
    @Inject
    Lazy<ProductFragment> mProductListFragmentLazy;

    @Override
    protected Fragment createFragment() {
        return mCategoryFragmentLazy.get();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
    }

    @Subscribe(tags = {RandomAction.TO_SHOW_DATA})
    public void toShowData(@NonNull RxChange rxChange) {
        addFragmentHideExisting(mProductListFragmentLazy.get());
    }
}
