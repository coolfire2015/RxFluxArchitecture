package com.huyingbao.module.wan.ui.tree.view;

import android.os.Bundle;

import com.huyingbao.core.common.view.CommonRxFragment;
import com.huyingbao.module.wan.R;
import com.huyingbao.module.wan.ui.tree.store.TreeStore;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

/**
 * Created by liujunfeng on 2018/12/26.
 */
public class TreeFragment extends CommonRxFragment<TreeStore> {
    @Nullable
    @Override
    public TreeStore getRxStore() {
        return ViewModelProviders.of(this, mViewModelFactory).get(TreeStore.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.common_fragment_base_list;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

    }
}
