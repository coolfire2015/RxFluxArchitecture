package com.huyingbao.module.gan.ui.random.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huyingbao.core.base.fragment.BaseRxFragment;
import com.huyingbao.module.gan.R;
import com.huyingbao.module.gan.ui.random.action.RandomActionCreator;
import com.huyingbao.module.gan.ui.random.adapter.ProductAdapter;
import com.huyingbao.module.gan.ui.random.model.Product;
import com.huyingbao.module.gan.ui.random.store.RandomStore;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public class ProductFragment extends BaseRxFragment<RandomStore> {
    private static final int PAGE_SIZE = 20;
    @Inject
    RandomActionCreator mActionCreator;

    RecyclerView mRvContent;

    private ProductAdapter mAdapter;

    public static ProductFragment newInstance() {
        return new ProductFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.common_fragment_list;
    }

    @Override
    public void afterCreate(@Nullable Bundle savedInstanceState) {
        setTitle(getRxStore().getCategory(), true);
        initRecyclerView();
        initAdapter();
        showData();
        //如果store已经创建并获取到数据，说明是横屏等操作导致的Fragment重建，不需要重新获取数据
        if (getRxStore().getProductListLiveData().getValue() != null) {
            return;
        }
        refresh();
    }

    /**
     * 实例化RecyclerView
     */
    private void initRecyclerView() {
        mRvContent = getView().findViewById(R.id.rv_content);
        mRvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvContent.setHasFixedSize(true);
        //硬件加速
        mRvContent.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    /**
     * 实例化adapter
     */
    private void initAdapter() {
        mAdapter = new ProductAdapter(null);
        //设置更多view
//        mAdapter.setLoadMoreView(new CommonLoadMoreView());
        //设置加载更多监听器
        mAdapter.setOnLoadMoreListener(() -> loadMore(), mRvContent);
        //view设置适配器
        mRvContent.setAdapter(mAdapter);
    }

    /**
     * 显示数据
     */
    private void showData() {
        getRxStore().getProductListLiveData().observe(this, productList -> {
            //判断获取回来的数据是否是刷新的数据
            boolean isRefresh = getRxStore().getNextRequestPage() == 1;
            setData(isRefresh, productList);
            mAdapter.setEnableLoadMore(true);
        });
    }

    /**
     * 刷新
     */
    private void refresh() {
        getRxStore().setNextRequestPage(1);
        //这里的作用是防止下拉刷新的时候还可以上拉加载
        mAdapter.setEnableLoadMore(false);
        mActionCreator.getDataList(getRxStore().getCategory(), PAGE_SIZE, getRxStore().getNextRequestPage());
    }

    /**
     * 加载更多
     */
    private void loadMore() {
        mActionCreator.getDataList(getRxStore().getCategory(), PAGE_SIZE, getRxStore().getNextRequestPage());
    }

    /**
     * 设置数据
     *
     * @param isRefresh
     * @param data
     */
    private void setData(boolean isRefresh, List<Product> data) {
        final int size = data == null || data.size() == 0 ? 0 : data.size() % PAGE_SIZE;
        mAdapter.setNewData(data);
        if (size == 0) {
            mAdapter.loadMoreComplete();
        } else {//最后一页取回的数据不到PAGE_SIZE，说明没有更多数据，结束加载更多操作
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isRefresh);
            Toast.makeText(getContext(), "no more data", Toast.LENGTH_SHORT).show();
        }
    }
}
