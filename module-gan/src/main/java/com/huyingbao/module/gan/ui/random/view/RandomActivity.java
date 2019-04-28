package com.huyingbao.module.gan.ui.random.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.base.view.BaseActivity;
import com.huyingbao.module.gan.ui.random.action.RandomAction;
import com.huyingbao.module.gan.ui.random.store.RandomStore;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public class RandomActivity extends BaseActivity<RandomStore> {
    @Override
    protected Fragment createFragment() {
        return CategoryFragment.newInstance();
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
    }

    @Subscribe(tags = {RandomAction.TO_SHOW_DATA}, sticky = true)
    public void toShowData(@NonNull RxChange rxChange) {
        addFragmentHideExisting(ProductFragment.newInstance());
    }
}
