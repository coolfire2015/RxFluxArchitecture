package com.huyingbao.module.gan.ui.category.view;

import android.os.Bundle;

import com.huyingbao.core.arch.scope.ActivityScope;
import com.huyingbao.core.common.R2;
import com.huyingbao.core.common.view.CommonRxFragment;
import com.huyingbao.module.gan.R;
import com.huyingbao.module.gan.ui.category.action.CategoryActionCreator;
import com.huyingbao.module.gan.ui.category.store.CategoryStore;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@ActivityScope
public class CategoryFragment extends CommonRxFragment<CategoryStore> {
    @Inject
    CategoryActionCreator mActionCreator;
    @BindView(R2.id.rv_content)
    RecyclerView mRvContent;

    @Inject
    public CategoryFragment() {
    }

    @Nullable
    @Override
    public CategoryStore getRxStore() {
        return ViewModelProviders.of(this, mViewModelFactory).get(CategoryStore.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.common_fragment_base_list;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
    }
}
