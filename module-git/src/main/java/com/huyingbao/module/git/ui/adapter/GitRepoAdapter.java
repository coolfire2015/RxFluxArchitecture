package com.huyingbao.module.git.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huyingbao.module.git.R;
import com.huyingbao.module.git.ui.model.GitRepo;

import java.util.List;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public class GitRepoAdapter extends BaseQuickAdapter<GitRepo, BaseViewHolder> {
    public GitRepoAdapter(@Nullable List<GitRepo> data) {
        super(R.layout.item_git_repo, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GitRepo item) {
        helper.setText(R.id.tv_git_repo_name, item.getName())
                .setText(R.id.tv_git_repo_description, item.getDescription())
                .setText(R.id.tv_git_repo_id, "GithubId" + item.getId());
    }
}
