package com.huyingbao.module.main.ui.main;

import android.os.Bundle;

import com.huyingbao.core.scope.ActivityScope;
import com.huyingbao.module.main.MainModuleFragment;
import com.huyingbao.module.main.R;
import com.huyingbao.module.main.R2;
import com.huyingbao.module.main.action.MainActions;

import javax.inject.Inject;

import butterknife.OnClick;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@ActivityScope
public class MainFragment extends MainModuleFragment {
    @Inject
    public MainFragment() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        initActionBar(null, false);
    }

    @OnClick(R2.id.btn_main_to_product)
    public void toProductList() {
        mActionCreator.postLocalAction(MainActions.TO_PRODUCT_LIST);
    }

    @OnClick(R2.id.btn_main_to_github)
    public void toGithub() {
        mActionCreator.postLocalAction(MainActions.TO_GITHUB);
    }
}
