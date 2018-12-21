package com.huyingbao.module.git.ui.view;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.huyingbao.core.arch.scope.ActivityScope;
import com.huyingbao.core.common.R2;
import com.huyingbao.core.common.view.CommonFragment;
import com.huyingbao.module.git.R;
import com.huyingbao.module.git.ui.adapter.GitRepoAdapter;
import com.huyingbao.module.git.ui.model.GitRepo;
import com.huyingbao.module.git.ui.action.GitActionCreator;
import com.huyingbao.module.git.ui.action.GitAction;
import com.huyingbao.module.git.ui.module.GitStore;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@ActivityScope
public class GitRepoFragment extends CommonFragment {
    @BindView(R2.id.rv_content)
    protected RecyclerView mRvContent;
    @BindView(R2.id.cl_content)
    protected CoordinatorLayout mClContent;
    protected List<GitRepo> mDataList = new ArrayList();
    protected BaseQuickAdapter mAdapter = new GitRepoAdapter(mDataList);
    @Inject
    GitActionCreator mActionCreator;
    private GitStore mStore;

    @Inject
    public GitRepoFragment() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_list;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mStore = ViewModelProviders.of(getActivity(), mViewModelFactory).get(GitStore.class);
        initActionBar("仓库列表");
        initRecyclerView();
        showData();
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
                toGitUser(position);
            }
        });
    }

    private void showData() {
        if (mStore.mGitRepoList.getValue() == null) mActionCreator.getGitRepoList();
        mStore.mGitRepoList.observe(this, products -> {
            mDataList.clear();
            if (products != null && products.size() > 0) mDataList.addAll(products);
            mAdapter.notifyDataSetChanged();
        });
    }

    private void toGitUser(int position) {
        mStore.mGitUser.setValue(null);
        mActionCreator.gitGitUser(getActivity(), mDataList.get(position).getOwner().getId());
        mActionCreator.postLocalAction(GitAction.TO_GIT_USER);
    }

}
