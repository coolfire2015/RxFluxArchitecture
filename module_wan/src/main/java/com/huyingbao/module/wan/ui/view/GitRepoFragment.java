package com.huyingbao.module.wan.ui.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.huyingbao.core.arch.model.RxError;
import com.huyingbao.core.arch.scope.ActivityScope;
import com.huyingbao.core.common.R2;
import com.huyingbao.core.common.view.CommonRxFragment;
import com.huyingbao.core.common.widget.CommonLoadMoreView;
import com.huyingbao.module.wan.R;
import com.huyingbao.module.wan.ui.action.WanAction;
import com.huyingbao.module.wan.ui.action.WanActionCreator;
import com.huyingbao.module.wan.ui.adapter.WanAdapter;
import com.huyingbao.module.wan.ui.model.GitRepo;
import com.huyingbao.module.wan.ui.module.WanStore;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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
public class GitRepoFragment extends CommonRxFragment<WanStore> {
    @Inject
    WanActionCreator mActionCreator;
    @BindView(R2.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R2.id.srl_content)
    SwipeRefreshLayout mSrlContent;

    private List<GitRepo> mDataList;
    private BaseQuickAdapter mAdapter;

    private int mNextRequestPage = 1;
    private static final int PAGE_SIZE = 20;

    @Inject
    public GitRepoFragment() {
    }

    @Nullable
    @Override
    public WanStore getRxStore() {
        return ViewModelProviders.of(getActivity(), mViewModelFactory).get(WanStore.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_list;
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

    @Subscribe(tags = {@Tag(WanAction.GET_ARTICLE_LIST)})
    public void getGitRepoList(RxError action) {
        Logger.e("asdf");
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
                toGitUser(position);
            }
        });
    }

    /**
     * 实例化adapter
     */
    private void initAdapter() {
        mDataList = new ArrayList();
        mAdapter = new WanAdapter(mDataList);
        //设置加载更多监听器
        mAdapter.setOnLoadMoreListener(() -> loadMore(), mRvContent);
        //设置加载动画
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        //view设置适配器
        mRvContent.setAdapter(mAdapter);
    }

    /**
     * 添加头部view
     */
    private void addHeadView() {
        View headView = getLayoutInflater().inflate(R.layout.view_head, (ViewGroup) mRvContent.getParent(), false);
        headView.findViewById(R.id.iv_head).setVisibility(View.GONE);
        ((TextView) headView.findViewById(R.id.tv_head)).setText("change load view");
        headView.setOnClickListener(v -> {
            mAdapter.setNewData(null);
            mAdapter.setLoadMoreView(new CommonLoadMoreView());
            mRvContent.setAdapter(mAdapter);
            Toast.makeText(getActivity(), "change complete", Toast.LENGTH_LONG).show();
            mSrlContent.setRefreshing(true);
            refresh();
        });
        mAdapter.addHeaderView(headView);
    }

    /**
     * 实例化view
     */
    private void initRefreshLayout() {
        mSrlContent.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSrlContent.setOnRefreshListener(() -> refresh());
    }

    /**
     * 页面跳转
     * * @param position
     */
    private void toGitUser(int position) {
        getRxStore().getGitUser().setValue(null);
        mActionCreator.gitGitUser(getActivity(), mDataList.get(position).getOwner().getId());
        mActionCreator.postLocalAction(WanAction.TO_GIT_USER);
    }

    /**
     * 显示数据
     */
    private void showData() {
        getRxStore().getGitRepoList().observe(this, products -> {
            if (products == null) return;
            //判断获取回来的数据是否是刷新的数据
            boolean isRefresh = mNextRequestPage == 1;
            setData(isRefresh, products);
            mAdapter.setEnableLoadMore(true);
            mSrlContent.setRefreshing(false);
        });
    }

    /**
     * 刷新
     */
    private void refresh() {
        mNextRequestPage = 1;
        mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        mActionCreator.getArticleList(mNextRequestPage);
    }

    /**
     * 加载更多
     */
    private void loadMore() {
        mActionCreator.getArticleList(mNextRequestPage);
    }

    /**
     * 设置数据
     *
     * @param isRefresh
     * @param data
     */
    private void setData(boolean isRefresh, List<GitRepo> data) {
        mNextRequestPage++;
        final int size = data == null
                ? 0
                : data.size();
        if (isRefresh) {
            mAdapter.setNewData(data);
        } else {
            if (size > 0) {
                mAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isRefresh);
            Toast.makeText(getContext(), "no more data", Toast.LENGTH_SHORT).show();
        } else {
            mAdapter.loadMoreComplete();
        }
    }
}
