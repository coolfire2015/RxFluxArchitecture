package com.huyingbao.module.gan.ui.random.view;

import android.os.Bundle;

import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.common.rxview.CommonRxActivity;
import com.huyingbao.module.gan.ui.random.action.RandomAction;
import com.huyingbao.module.gan.ui.random.store.RandomStore;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import dagger.Lazy;

/**
 * @author liujunfeng
 * @date 2019/1/1
 */
public class RandomActivity extends CommonRxActivity<RandomStore> {
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

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onRxChanged(@NonNull RxChange rxChange) {
        super.onRxChanged(rxChange);
        switch (rxChange.getTag()) {
            case RandomAction.TO_SHOW_DATA:
                addFragmentHideExisting(mProductListFragmentLazy.get());
                break;
            default:
                break;
        }
    }
}
