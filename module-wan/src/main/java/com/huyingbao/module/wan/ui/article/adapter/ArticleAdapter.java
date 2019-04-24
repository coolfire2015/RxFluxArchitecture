package com.huyingbao.module.wan.ui.article.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huyingbao.module.wan.R;
import com.huyingbao.module.wan.ui.article.model.Article;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public class ArticleAdapter extends BaseQuickAdapter<Article, BaseViewHolder> {
    public ArticleAdapter(@Nullable List<Article> data) {
        super(R.layout.wan_recycle_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Article item) {
        helper.setText(R.id.tv_item_name, item.getAuthor())
                .setText(R.id.tv_item_description, item.getDesc())
                .setText(R.id.tv_item_id, "GithubId" + item.getId());
    }
}
