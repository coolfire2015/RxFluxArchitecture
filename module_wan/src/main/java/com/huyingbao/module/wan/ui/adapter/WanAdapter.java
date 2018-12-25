package com.huyingbao.module.wan.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huyingbao.module.wan.R;
import com.huyingbao.module.wan.ui.model.Article;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public class WanAdapter extends BaseQuickAdapter<Article, BaseViewHolder> {
    public WanAdapter(@Nullable List<Article> data) {
        super(R.layout.item_git_repo, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Article item) {
        helper.setText(R.id.tv_git_repo_name, item.getAuthor())
                .setText(R.id.tv_git_repo_description, item.getDesc())
                .setText(R.id.tv_git_repo_id, "GithubId" + item.getId());
    }
}
