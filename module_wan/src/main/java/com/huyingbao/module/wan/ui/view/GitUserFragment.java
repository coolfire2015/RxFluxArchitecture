package com.huyingbao.module.wan.ui.view;

import android.os.Bundle;
import android.widget.TextView;

import com.huyingbao.core.arch.scope.ActivityScope;
import com.huyingbao.core.common.view.CommonRxFragment;
import com.huyingbao.module.wan.R;
import com.huyingbao.module.wan.R2;
import com.huyingbao.module.wan.ui.model.GitUser;
import com.huyingbao.module.wan.ui.module.WanStore;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;

/**
 * Created by liujunfeng on 2017/12/7.
 */
@ActivityScope
public class GitUserFragment extends CommonRxFragment<WanStore> {
    @BindView(R2.id.tv_git_user_name)
    TextView mTvGitUserName;
    @BindView(R2.id.tv_git_user_login)
    TextView mTvGitUserLogin;
    @BindView(R2.id.tv_git_user_statistics)
    TextView mTvGitUserStatistics;

    @Inject
    public GitUserFragment() {

    }

    @Nullable
    @Override
    public WanStore getRxStore() {
        return ViewModelProviders.of(getActivity(), mViewModelFactory).get(WanStore.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_git_user;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        initActionBar("用户信息");
        getRxStore().getGitUser().observe(this, shop -> showGitUserInfo(shop));
    }

    /**
     * 显示用户信息
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
