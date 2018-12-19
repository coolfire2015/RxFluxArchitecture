package com.huyingbao.module.gan.ui.random;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.huyingbao.core.action.RxActionError;
import com.huyingbao.core.common.CommonFragment;
import com.huyingbao.core.scope.ActivityScope;
import com.huyingbao.core.store.RxStore;
import com.huyingbao.core.store.RxStoreChange;
import com.huyingbao.module.gan.R;
import com.huyingbao.module.gan.R2;
import com.huyingbao.module.gan.ui.random.action.RandomActionCreator;
import com.huyingbao.module.gan.ui.random.action.RandomActions;

import java.util.List;

import javax.inject.Inject;

import butterknife.OnClick;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@ActivityScope
public class RandomFragment extends CommonFragment {
    @Inject
    RandomActionCreator mActionCreator;

    @Inject
    public RandomFragment() {
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
        mActionCreator.postLocalAction(RandomActions.TO_PRODUCT_LIST);
    }

    @OnClick(R2.id.btn_main_to_github)
    public void toGithub() {
        mActionCreator.postLocalAction(RandomActions.TO_GITHUB);
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {

    }

    @Override
    public void onRxError(@NonNull RxActionError error) {

    }

    @Nullable
    @Override
    public List<RxStore> getLifecycleRxStoreList() {
        return null;
    }
}
