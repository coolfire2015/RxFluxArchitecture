package com.huyingbao.module.wan.ui.article.view;

import android.os.Bundle;
import android.view.View;

import com.huyingbao.core.arch.scope.ActivityScope;
import com.huyingbao.core.base.rxview.BaseRxFragment;
import com.huyingbao.core.common.R2;
import com.huyingbao.module.wan.R;
import com.huyingbao.module.wan.ui.article.action.ArticleActionCreator;
import com.huyingbao.module.wan.ui.article.adapter.BannerAdapter;
import com.huyingbao.module.wan.ui.article.model.Banner;
import com.huyingbao.module.wan.ui.article.store.ArticleStore;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by liujunfeng on 2019/1/1.
 */
@ActivityScope
public class BannerFragment extends BaseRxFragment<ArticleStore> {
    @Inject
    ArticleActionCreator mActionCreator;

    @BindView(R2.id.rv_content)
    RecyclerView mRvContent;

    private List<Banner> mDataList;
    private BannerAdapter mAdapter;

    @Inject
    public BannerFragment() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.common_fragment_list;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        setTitle(R.string.wan_label_banner, true);
        initRecyclerView();
        initAdapter();
        showData();
        //如果store已经创建并获取到数据，说明是横屏等操作导致的Fragment重建，不需要重新获取数据
        if (getRxStore().getBannerLiveData().getValue() != null) {
            return;
        }
        refresh();
    }

    /**
     * 实例化RecyclerView
     */
    private void initRecyclerView() {
        mRvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvContent.setHasFixedSize(true);
        //硬件加速
        mRvContent.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    /**
     * 实例化adapter
     */
    private void initAdapter() {
        mDataList = new ArrayList();
        mAdapter = new BannerAdapter(mDataList);
        //view设置适配器
        mRvContent.setAdapter(mAdapter);
    }

    /**
     * 显示数据
     */
    private void showData() {
        getRxStore().getBannerLiveData().observe(this, bannerArrayList -> setData(bannerArrayList));
    }

    /**
     * 刷新
     */
    private void refresh() {
        mActionCreator.getBannerList();
    }

    /**
     * 设置数据
     *
     * @param data
     */
    private void setData(List<Banner> data) {
        mAdapter.setNewData(data);
    }
}
