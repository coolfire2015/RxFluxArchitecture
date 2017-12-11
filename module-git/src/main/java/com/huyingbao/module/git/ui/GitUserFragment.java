package com.huyingbao.module.git.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.widget.TextView;

import com.huyingbao.core.custom.CommonFragment;
import com.huyingbao.core.scope.ActivityScope;
import com.huyingbao.module.git.R;
import com.huyingbao.module.git.R2;
import com.huyingbao.module.git.action.GitActionCreator;
import com.huyingbao.module.git.ui.model.GitUser;
import com.huyingbao.module.git.ui.module.GitStore;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@ActivityScope
public class GitUserFragment extends CommonFragment {
    @Inject
    GitActionCreator mActionCreator;
    @BindView(R2.id.tv_git_user_name)
    TextView mTvGitUserName;
    @BindView(R2.id.tv_git_user_login)
    TextView mTvGitUserLogin;
    @BindView(R2.id.tv_git_user_statistics)
    TextView mTvGitUserStatistics;
    private GitStore mStore;

    @Inject
    public GitUserFragment() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_git_user;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        initActionBar("店铺信息");
        mStore = ViewModelProviders.of(getActivity(), mViewModelFactory).get(GitStore.class);
        mStore.mGitUser.observe(this, shop -> showGitUserInfo(shop));
    }

    /**
     * 显示店铺信息
     *
     * @param gitUser
     */
    private void showGitUserInfo(GitUser gitUser) {
        if (gitUser == null) return;
        mTvGitUserLogin.setText(gitUser.getLogin() + "");
        mTvGitUserName.setText(gitUser.getName());
        mTvGitUserStatistics.setText(gitUser.getFollowers() + "");
    }
}
