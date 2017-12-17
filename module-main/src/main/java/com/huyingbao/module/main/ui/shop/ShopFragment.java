package com.huyingbao.module.main.ui.shop;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.huyingbao.core.scope.ActivityScope;
import com.huyingbao.module.main.MainModuleFragment;
import com.huyingbao.module.main.R;
import com.huyingbao.module.main.R2;
import com.huyingbao.module.main.ui.shop.model.Shop;
import com.huyingbao.module.main.ui.shop.module.ShopStore;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@ActivityScope
public class ShopFragment extends MainModuleFragment {
    @BindView(R2.id.tv_shop_name)
    TextView mTvShopName;
    @BindView(R2.id.tv_shop_login)
    TextView mTvShopLogin;
    @BindView(R2.id.tv_shop_statistics)
    TextView mTvShopStatistics;

    private ShopStore mStore;

    @Inject
    public ShopFragment() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_shop;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mStore = ViewModelProviders.of(getActivity(), mViewModelFactory).get(ShopStore.class);
        initActionBar("店铺信息");
        mStore.mShopTrans.observe(this, shop -> {
            if (shop != null) showShopInfo(shop);
        });
    }

    @OnClick(R2.id.btn_test)
    public void test() {
        mStore.setShopId(1);
    }

    /**
     * 显示店铺信息
     *
     * @param shop
     */
    private void showShopInfo(@NonNull Shop shop) {
        mTvShopLogin.setText(shop.getCode() + "");
        mTvShopName.setText(shop.getShopName());
        mTvShopStatistics.setText(shop.getShopType() + "");
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
