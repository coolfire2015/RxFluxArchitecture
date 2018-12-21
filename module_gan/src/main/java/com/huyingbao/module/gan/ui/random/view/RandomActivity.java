package com.huyingbao.module.gan.ui.random.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.huyingbao.core.arch.store.RxStore;
import com.huyingbao.core.common.util.ActivityUtils;
import com.huyingbao.core.common.view.CommonActivity;
import com.huyingbao.module.gan.R;
import com.huyingbao.module.gan.action.GanConstants;
import com.huyingbao.module.gan.ui.random.store.RandomStore;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import dagger.Lazy;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public class RandomActivity extends CommonActivity {
    @Inject
    Lazy<ProductFragment> mProductListFragmentLazy;

    private RandomStore mStore;

    public static Intent newIntent(Context context, String category) {
        Intent intent = new Intent(context, RandomActivity.class);
        intent.putExtra(GanConstants.Key.CATEGORY, category);
        return intent;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mStore = ViewModelProviders.of(this, mViewModelFactory).get(RandomStore.class);
        //TODO 需要优化
        mStore.setCategory(getIntent().getStringExtra(GanConstants.Key.CATEGORY));
        ActivityUtils.addFragment(getSupportFragmentManager(),
                mProductListFragmentLazy.get(),
                R.id.fl_content);
    }

    @Nullable
    @Override
    public List<RxStore> getLifecycleRxStoreList() {
        return Collections.singletonList(mStore);
    }
}
