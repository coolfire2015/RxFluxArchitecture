package com.huyingbao.module.wan.ui.common.friend.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.arch.scope.ActivityScope;
import com.huyingbao.core.common.R2;
import com.huyingbao.core.common.view.CommonRxFragment;
import com.huyingbao.module.wan.R;
import com.huyingbao.module.wan.ui.common.friend.action.FriendAction;
import com.huyingbao.module.wan.ui.common.friend.action.FriendActionCreator;
import com.huyingbao.module.wan.ui.common.friend.adapter.WebSiteAdapter;
import com.huyingbao.module.wan.ui.common.friend.model.WebSite;
import com.huyingbao.module.wan.ui.common.friend.store.FriendStore;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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
    @BindView(R2.id.srl_content)
    SwipeRefreshLayout mSrlContent;

    private List<WebSite> mDataList;
    private BaseQuickAdapter mAdapter;

    @Inject
    public FriendFragment() {
    }

    @Nullable
    @Override
    public FriendStore getRxStore() {
        return ViewModelProviders.of(this, mViewModelFactory).get(FriendStore.class);
    }


    @Override
    public int getLayoutId() {
        return R.layout.common_fragment_base_list;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        initActionBar("仓库列表");
        initRecyclerView();
        initAdapter();
        //addHeadView();
        initRefreshLayout();
        showData();
        //如果store已经创建并获取到数据，说明是横屏等操作导致的Fragment重建，不需要重新获取数据
        if (getRxStore().isCreated()) return;
        mSrlContent.setRefreshing(true);
        refresh();
    }

    /**
     * 接收RxChange，粘性
     */
    @Override
    @Subscribe(sticky = true)
    public void onRxChanged(@NonNull RxChange rxChange) {
        super.onRxChanged(rxChange);
        switch (rxChange.getTag()) {
            case FriendAction.GET_FRIEND_LIST:
//                startActivity(RandomActivity.newIntent(this, getRxStore().getCategory()));
                break;
        }
    }

    /**
     * 实例化RecyclerView
     */
    private void initRecyclerView() {
        mRvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvContent.setHasFixedSize(true);
        mRvContent.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//硬件加速
        mRvContent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
    }

    /**
     * 实例化adapter
     */
    private void initAdapter() {
        mDataList = new ArrayList();
        mAdapter = new WebSiteAdapter(mDataList);
        //设置加载动画
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        //view设置适配器
        mRvContent.setAdapter(mAdapter);
    }

    /**
     * 实例化view
     */
    private void initRefreshLayout() {
        mSrlContent.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSrlContent.setOnRefreshListener(() -> refresh());
    }

    /**
     * 显示数据
     */
    private void showData() {
        getRxStore().getWebSiteListData().observe(this, products -> {
            if (products == null) return;
            setData(products.getData());
            mAdapter.setEnableLoadMore(true);
            mSrlContent.setRefreshing(false);
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
