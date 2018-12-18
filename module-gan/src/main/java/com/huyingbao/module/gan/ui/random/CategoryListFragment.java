package com.huyingbao.module.gan.ui.random;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.huyingbao.core.common.CommonFragment;
import com.huyingbao.core.common.R2;
import com.huyingbao.core.scope.ActivityScope;
import com.huyingbao.module.gan.R;
import com.huyingbao.module.gan.ui.random.adapter.ProductAdapter;
import com.huyingbao.module.gan.ui.random.model.Product;
import com.huyingbao.module.gan.ui.random.store.RandomStore;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@ActivityScope
public class CategoryListFragment extends CommonFragment {
    @BindView(R2.id.rv_content)
    protected RecyclerView mRvContent;
    @BindView(R2.id.cl_content)
    protected CoordinatorLayout mClContent;

    protected List<Product> mDataList = new ArrayList();
    protected BaseQuickAdapter mAdapter = new ProductAdapter(mDataList);
    private RandomStore mStore;

    @Inject
    public CategoryListFragment() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_list;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mStore = ViewModelProviders.of(getActivity(), mViewModelFactory).get(RandomStore.class);
        initActionBar("首页");
        initRecyclerView();
        showData();
        mStore.setPage(0);
    }

    /**
     * 实例化RecyclerView,并设置adapter
     */
    protected void initRecyclerView() {
        mRvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvContent.setHasFixedSize(true);
        mRvContent.setAdapter(mAdapter);
        mRvContent.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//硬件加速
        mRvContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
    }

    private void showData() {
        mStore.mProductTrans.observe(this, products -> {
            mDataList.clear();
            if (products != null && products.getResults().size() > 0)
                mDataList.addAll(products.getResults());
            mAdapter.notifyDataSetChanged();
        });
    }
}
