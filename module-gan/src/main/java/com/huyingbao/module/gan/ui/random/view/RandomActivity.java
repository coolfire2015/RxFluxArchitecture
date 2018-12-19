package com.huyingbao.module.gan.ui.random.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.huyingbao.core.store.RxStore;
import com.huyingbao.core.store.RxStoreChange;
import com.huyingbao.core.util.ActivityUtils;
import com.huyingbao.core.view.CommonActivity;
import com.huyingbao.module.gan.R;
import com.huyingbao.module.gan.action.GanContants;
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
    Lazy<ProductFragment> mProductListFragmentLazy;
    private RandomStore mRandomStore;

    public static Intent newIntent(Context context, String category) {
        Intent intent = new Intent(context, RandomActivity.class);
        intent.putExtra(GanContants.Key.CATEGORY, category);
        return intent;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mRandomStore = ViewModelProviders.of(this, mViewModelFactory).get(RandomStore.class);
        mRandomStore.setCategory(getIntent().getStringExtra(GanContants.Key.CATEGORY));
        ActivityUtils.addFragment(getSupportFragmentManager(),
                mProductListFragmentLazy.get(),
                R.id.fl_content);
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
    }

    @Nullable
    @Override
    public List<RxStore> getLifecycleRxStoreList() {
        return Collections.singletonList(mRandomStore);
    }
}
