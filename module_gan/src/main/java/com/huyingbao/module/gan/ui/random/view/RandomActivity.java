package com.huyingbao.module.gan.ui.random.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.huyingbao.core.common.view.CommonRxActivity;
import com.huyingbao.module.gan.action.GanConstants;
import com.huyingbao.module.gan.ui.random.store.RandomStore;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import dagger.Lazy;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public class RandomActivity extends CommonRxActivity<RandomStore> {
    @Inject
    Lazy<ProductFragment> mProductListFragmentLazy;

    public static Intent newIntent(Context context, String category) {
        Intent intent = new Intent(context, RandomActivity.class);
        intent.putExtra(GanConstants.Key.CATEGORY, category);
        return intent;
    }

    @Nullable
    @Override
    public RandomStore getRxStore() {
        return ViewModelProviders.of(this, mViewModelFactory).get(RandomStore.class);
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        getRxStore().setCategory(getIntent().getStringExtra(GanConstants.Key.CATEGORY));
    }

    @Override
    protected Fragment createFragment() {
        return mProductListFragmentLazy.get();
    }
}
