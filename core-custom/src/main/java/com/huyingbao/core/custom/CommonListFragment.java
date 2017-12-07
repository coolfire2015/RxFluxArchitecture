package com.huyingbao.core.custom;

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
import com.huyingbao.core.action.RxError;
import com.huyingbao.core.common.R;
import com.huyingbao.core.common.R2;
import com.huyingbao.core.dispatcher.RxViewDispatch;
import com.huyingbao.core.store.RxStore;
import com.huyingbao.core.store.RxStoreChange;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 带上拉加载,下拉刷新的Fragment
 * Created by liujunfeng on 2017/12/7.
 */
public abstract class CommonListFragment<T> extends CommonFragment implements RxViewDispatch {
    @BindView(R2.id.rv_content)
    protected RecyclerView mRvContent;
    @BindView(R2.id.srl_content)
    protected SwipeRefreshLayout mSrlContent;
    @BindView(R2.id.cl_content)
    protected CoordinatorLayout mClContent;

    protected List<T> mDataList = new ArrayList();
    protected LinearLayoutManager mLinearLayoutManager;
    protected BaseQuickAdapter mAdapter;

    protected int mFirstIndex = 0;//初始索引,用于第一次获取数据和刷新获取数据
    protected int mNextIndex;//加载更多数据索引

    protected int mLimit = 20;//每页数据个数
    protected int mLastVisiblePosition = -1;
    protected int mFirstVisiblePosition = -1;
    protected boolean isRefresh;
    protected boolean isLoadingMore = false;//是否需要加载更多,true:需要加载更多,false:加载完成
    protected boolean scrollState;//true上拉,false下拉

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_list;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        initAdapter();
        initRecyclerView();
        initSwipeRefreshLayout();
        refresh();
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
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mRvContent.setLayoutManager(mLinearLayoutManager);
        mRvContent.setHasFixedSize(true);
        mRvContent.setAdapter(mAdapter);
        mRvContent.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//硬件加速
        mRvContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollState = dy > 0;
                if (scrollState) {//上拉
                    mLastVisiblePosition = mLinearLayoutManager.findLastVisibleItemPosition();
                } else {//下拉
                    mFirstVisiblePosition = mLinearLayoutManager.findFirstCompletelyVisibleItemPosition();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //滑动未停止
                if (newState != RecyclerView.SCROLL_STATE_IDLE) return;
                if (scrollState) {//上拉
                    if (mLastVisiblePosition + 1 == mAdapter.getItemCount() && isLoadingMore) { //当前显示的数据是最后一条且页面有数据需要加载
                        if (mSrlContent != null) mSrlContent.setRefreshing(true);
                        isLoadingMore = false;
                        getDataList(mNextIndex);
                    }
                } else {//下拉
                    if (mFirstVisiblePosition == 0)
                        refresh();//第一条数据,刷新
                }
            }
        });
    }

    /**
     * 实例化刷新layout
     */
    protected void initSwipeRefreshLayout() {
        //设置进度颜色
        mSrlContent.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        // 这句话是为了，第一次进入页面的时候显示加载进度条
        mSrlContent.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        //防止下拉刷新冲突,数据太多时,Recyclerview上拉显示数据和SwipeRefreshLayout上拉刷新产生冲突,需要禁掉SwipeRefreshLayout根据手势的上拉刷新
        mSrlContent.setEnabled(false);
    }

    /**
     * 首次加载数据或者刷新时调用
     */
    protected void refresh() {
        mSrlContent.setRefreshing(true);
        isRefresh = true;
        mNextIndex = mFirstIndex;
        getDataList(mFirstIndex);
    }

    /**
     * 处理并显示数据
     *
     * @param list
     */
    protected void showDataList(List<T> list) {
        //停止刷新动画T
        mSrlContent.setRefreshing(false);
        //返回的没有数据,或者返回的为空
        if (list == null || list.size() == 0) {
            isLoadingMore = false;//停止加载数据
            if (isRefresh) {
                mDataList.clear();
                mAdapter.notifyDataSetChanged();
            }
            return;
        }
        //返回数据不为空,可以继续加载
        isLoadingMore = true;
        if (isRefresh) {//刷新先把原来数据清空
            isRefresh = false;
            mDataList.clear();
        }
        //添加数据
        mDataList.addAll(list);
        mAdapter.notifyDataSetChanged();
        //更新加载索引
        updateLoadingIndex();
    }

    /**
     * 更新加载索引
     */
    protected void updateLoadingIndex() {
        mNextIndex = mNextIndex + mLimit;
    }

    /**
     * 实例化adapter
     */
    protected abstract void initAdapter();

    /**
     * 获取数据
     */
    protected abstract void getDataList(int index);
}