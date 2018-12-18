package com.huyingbao.module.gan.ui.main;

import android.os.Bundle;

import com.huyingbao.core.scope.ActivityScope;
import com.huyingbao.module.gan.GanModuleFragment;
import com.huyingbao.module.main.R;
import com.huyingbao.module.main.R2;
import com.huyingbao.module.gan.action.MainActions;

import javax.inject.Inject;

import butterknife.OnClick;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@ActivityScope
public class GanFragment extends GanModuleFragment {
    @Inject
    public GanFragment() {
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
