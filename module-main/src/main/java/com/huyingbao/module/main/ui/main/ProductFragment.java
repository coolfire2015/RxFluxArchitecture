package com.huyingbao.module.main.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.huyingbao.core.action.RxError;
import com.huyingbao.core.common.R2;
import com.huyingbao.core.custom.CommonFragment;
import com.huyingbao.core.dispatcher.RxViewDispatch;
import com.huyingbao.core.scope.PerActivity;
import com.huyingbao.core.store.RxStore;
import com.huyingbao.core.store.RxStoreChange;
import com.huyingbao.module.main.R;
import com.huyingbao.module.main.action.MainActionCreator;
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
@PerActivity
public class ProductFragment extends CommonFragment implements RxViewDispatch {
    @BindView(R2.id.rv_content)
    protected RecyclerView mRvContent;
    @BindView(R2.id.srl_content)
    protected SwipeRefreshLayout mSrlContent;
    @BindView(R2.id.cl_content)
    protected CoordinatorLayout mClContent;

    @Inject
    MainActionCreator mActionCreator;
    private MainStore mStore;

    protected List<Product> mDataList = new ArrayList();
    protected BaseQuickAdapter mAdapter = new ProductAdapter(mDataList);

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
        mStore = ViewModelProviders.of(getActivity(), mViewModelFactory).get(MainStore.class);
        mStore.getProductList().observe(this, products -> showDataList(products));

        initRecyclerView();
        initSwipeRefreshLayout();
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
    }

    @Override
    public void onRxError(@NonNull RxError error) {
        mSrlContent.setRefreshing(false);
    }

    @Nullable
    @Override
    public List<RxStore> getLifecycleRxStoreList() {
        return null;
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
                mStore.mShop.setValue(null);
                mActionCreator.getShop(mContext, mDataList.get(position).getShopId().getShopId());
                mActionCreator.postLocalAction(MainActions.TO_SHOP);
            }
        });
    }

    /**
     * 实例化刷新layout
     */
    protected void initSwipeRefreshLayout() {
        //设置进度颜色
        mSrlContent.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        //这句话是为了，第一次进入页面的时候显示加载进度条
        mSrlContent.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        mSrlContent.setRefreshing(true);
    }

    /**
     * 处理并显示数据
     *
     * @param list
     */
    protected void showDataList(List<Product> list) {
        //停止刷新动画T
        mSrlContent.setRefreshing(false);
        //返回的没有数据,或者返回的为空
        if (list == null || list.size() == 0) return;
        //返回数据不为空,可以继续加载
        mDataList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }
}
