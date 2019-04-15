package com.huyingbao.module.wan.ui.article.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.huyingbao.core.arch.scope.ActivityScope;
import com.huyingbao.core.common.R2;
import com.huyingbao.core.base.rxview.BaseRxFragment;
import com.huyingbao.core.common.widget.CommonLoadMoreView;
import com.huyingbao.module.wan.R;
import com.huyingbao.module.wan.ui.article.action.ArticleAction;
import com.huyingbao.module.wan.ui.article.action.ArticleActionCreator;
import com.huyingbao.module.wan.ui.article.adapter.ArticleAdapter;
import com.huyingbao.module.wan.ui.article.model.Article;
import com.huyingbao.module.wan.ui.article.store.ArticleStore;

import java.util.List;

import javax.inject.Inject;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by liujunfeng on 2019/1/1.
 */
@ActivityScope
public class ArticleListFragment extends BaseRxFragment<ArticleStore> {
    private static final int PAGE_SIZE = 20;
    @Inject
    ArticleActionCreator mActionCreator;
    @BindView(R2.id.rv_content)
    RecyclerView mRvContent;

    private BaseQuickAdapter mAdapter;

    @Inject
    public ArticleListFragment() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.common_fragment_list;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        setTitle(R.string.wan_label_article, true);
        initRecyclerView();
        initAdapter();
        showData();
        //如果store已经创建并获取到数据，说明是横屏等操作导致的Fragment重建，不需要重新获取数据
        if (getRxStore().getArticleLiveData().getValue() != null) {
            return;
        }
        refresh();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //菜单布局文件名同界面布局文件名
        inflater.inflate(R.menu.wan_fragment_article_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_to_banner) {
            mActionCreator.postLocalChange(ArticleAction.TO_BANNER);
            return true;
        } else if (item.getItemId() == R.id.menu_to_friend) {
            mActionCreator.postLocalChange(ArticleAction.TO_FRIEND);
            return true;
        } else if (item.getItemId() == R.id.menu_to_login) {
            mActionCreator.postLocalChange(ArticleAction.TO_LOGIN);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
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
        mAdapter = new ArticleAdapter(null);
        //设置更多view
        mAdapter.setLoadMoreView(new CommonLoadMoreView());
        //设置加载更多监听器
        mAdapter.setOnLoadMoreListener(() -> loadMore(), mRvContent);
        //view设置适配器
        mRvContent.setAdapter(mAdapter);
    }

    /**
     * 显示数据
     */
    private void showData() {
        getRxStore().getArticleLiveData().observe(this, articleArrayList -> {
            //判断获取回来的数据是否是刷新的数据
            boolean isRefresh = getRxStore().getNextRequestPage() == 1;
            setData(isRefresh, articleArrayList);
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
        mActionCreator.getArticleList(getRxStore().getNextRequestPage());
    }

    /**
     * 加载更多
     */
    private void loadMore() {
        mActionCreator.getArticleList(getRxStore().getNextRequestPage());
    }

    /**
     * 设置数据
     *
     * @param isRefresh
     * @param data
     */
    private void setData(boolean isRefresh, List<Article> data) {
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
