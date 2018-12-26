package com.huyingbao.module.wan.ui.tree.view;

import android.os.Bundle;

import com.huyingbao.core.common.view.CommonRxFragment;
import com.huyingbao.module.wan.ui.tree.store.TreeStore;

import androidx.annotation.Nullable;

/**
 * Created by liujunfeng on 2018/12/26.
 */
public class TreeFragment extends CommonRxFragment<TreeStore> {
    @Nullable
    @Override
    public TreeStore getRxStore() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {

    }
}
