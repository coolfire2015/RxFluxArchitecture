package com.huyingbao.module.wan.ui.tree.view;

import android.os.Bundle;

import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.common.view.CommonRxActivity;
import com.huyingbao.module.wan.ui.tree.store.TreeStore;

import org.greenrobot.eventbus.Subscribe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

/**
 * Created by liujunfeng on 2018/12/26.
 */
public class TreeActivity extends CommonRxActivity<TreeStore> {
    @Nullable
    @Override
    public TreeStore getRxStore() {
        return ViewModelProviders.of(this, mViewModelFactory).get(TreeStore.class);
    }

    @Override
    protected Fragment createFragment() {
        return null;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
    }

    @Override
    @Subscribe(sticky = true)
    public void onRxChanged(@NonNull RxChange rxChange) {
        super.onRxChanged(rxChange);
    }
}
