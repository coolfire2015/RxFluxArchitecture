package com.huyingbao.module.wan.ui.friend.view;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.scope.ActivityScope;
import com.huyingbao.core.common.R2;
import com.huyingbao.core.common.view.CommonRxFragment;
import com.huyingbao.module.wan.R;
import com.huyingbao.module.wan.ui.friend.action.FriendActionCreator;
import com.huyingbao.module.wan.ui.friend.adapter.WebSiteAdapter;
import com.huyingbao.module.wan.ui.friend.model.WebSite;
import com.huyingbao.module.wan.ui.friend.store.FriendStore;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@ActivityScope
public class FriendFragment extends CommonRxFragment<FriendStore> {
    @Inject
    FriendActionCreator mActionCreator;

    @BindView(R2.id.rv_content)
    RecyclerView mRvContent;

    private List<WebSite> mDataList;
    private BaseQuickAdapter mAdapter;

    @Inject
    public FriendFragment() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.common_fragment_base_list;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        setTitle(R.string.wan_label_friend);
        initRecyclerView();
        initAdapter();
        showData();
        //如果store已经创建并获取到数据，说明是横屏等操作导致的Fragment重建，不需要重新获取数据
        if (getRxStore().isCreated()) return;
        refresh();
    }

    @Override
    @Subscribe(sticky = true)
    public void onRxChanged(@NonNull RxChange rxChange) {
        super.onRxChanged(rxChange);
    }

    /**
     * 实例化RecyclerView
     */
    private void initRecyclerView() {
        mRvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvContent.setHasFixedSize(true);
        mRvContent.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//硬件加速
    }

    /**
     * 实例化adapter
     */
    private void initAdapter() {
        mDataList = new ArrayList();
        mAdapter = new WebSiteAdapter(mDataList);
        //view设置适配器
        mRvContent.setAdapter(mAdapter);
    }

    /**
     * 显示数据
     */
    private void showData() {
        getRxStore().getWebSiteListData().observe(this, products -> {
            if (products == null) return;
            setData(products.getData());
        });
    }

    /**
     * 刷新
     */
    private void refresh() {
        mActionCreator.getFriendList();
    }

    /**
     * 设置数据
     *
     * @param data
     */
    private void setData(List<WebSite> data) {
        mAdapter.setNewData(data);
    }
}
