package com.huyingbao.module.main.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.huyingbao.core.common.R2;
import com.huyingbao.core.scope.ActivityScope;
import com.huyingbao.module.main.MainModuleFragment;
import com.huyingbao.module.main.R;
import com.huyingbao.module.main.action.MainActions;
import com.huyingbao.module.main.ui.main.adapter.ProductAdapter;
import com.huyingbao.module.main.ui.main.model.Product;
import com.huyingbao.module.main.ui.main.module.MainStore;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@ActivityScope
public class ProductFragment extends MainModuleFragment {
    @BindView(R2.id.rv_content)
    protected RecyclerView mRvContent;
    @BindView(R2.id.cl_content)
    protected CoordinatorLayout mClContent;

    protected List<Product> mDataList = new ArrayList();
    protected BaseQuickAdapter mAdapter = new ProductAdapter(mDataList);
    private MainStore mStore;

    @Inject
    public ProductFragment() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_list;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        initActionBar("商品列表");
        initRecyclerView();

        showData();
    }

    /**
     * 实例化RecyclerView,并设置adapter
     */
    protected void initRecyclerView() {
        mRvContent.setLayoutManager(new LinearLayoutManager(mContext));
        mRvContent.setHasFixedSize(true);
        mRvContent.setAdapter(mAdapter);
        mRvContent.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//硬件加速
        mRvContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                toShop(position);
            }
        });
    }

    private void showData() {
        mStore = ViewModelProviders.of(getActivity(), mViewModelFactory).get(MainStore.class);
        if (mStore.mProductList.getValue() == null) mActionCreator.getProductList();
        mStore.mProductList.observe(this, products -> {
            mDataList.clear();
            if (products != null && products.size() > 0) mDataList.addAll(products);
            mAdapter.notifyDataSetChanged();
        });
    }

    private void toShop(int position) {
        mStore.mShop.setValue(null);
        mActionCreator.getShop(mContext, mDataList.get(position).getShopId().getShopId());
        mActionCreator.postLocalAction(MainActions.TO_SHOP);
    }
}
