package com.huyingbao.module.main.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.huyingbao.core.custom.CommonListFragment;
import com.huyingbao.core.scope.PerActivity;
import com.huyingbao.module.main.action.MainActionCreator;
import com.huyingbao.module.main.action.MainActions;
import com.huyingbao.module.main.ui.main.adapter.ProductAdapter;
import com.huyingbao.module.main.ui.main.model.Product;
import com.huyingbao.module.main.ui.main.module.MainStore;

import javax.inject.Inject;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@PerActivity
public class ProductFragment extends CommonListFragment<Product> {
    @Inject
    MainActionCreator mActionCreator;
    private MainStore mStore;

    @Inject
    public ProductFragment() {

    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        initActionBar("商品列表");
        super.afterCreate(savedInstanceState);
        mStore = ViewModelProviders.of(getActivity(), mViewModelFactory).get(MainStore.class);
        mStore.getProductList().observe(this, products -> showDataList(products));
    }

    @Override
    protected void initAdapter() {
        mAdapter = new ProductAdapter(mDataList);
    }

    @Override
    protected void initRecyclerView() {
        super.initRecyclerView();
        mRvContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                mStore.getShopId().setValue(mDataList.get(position).getShopId().getShopId());
                mActionCreator.postLocalAction(MainActions.TO_SHOP);
            }
        });
    }

    @Override
    protected void getDataList(int index) {
        mActionCreator.getProductList();
    }
}
