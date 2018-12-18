package com.huyingbao.module.gan.ui.shop;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.huyingbao.core.scope.ActivityScope;
import com.huyingbao.module.gan.GanModuleFragment;
import com.huyingbao.module.gan.ui.shop.model.Shop;
import com.huyingbao.module.gan.ui.shop.module.ShopStore;
import com.huyingbao.module.gan.R;
import com.huyingbao.module.gan.R2;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@ActivityScope
public class ShopFragment extends GanModuleFragment {
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
