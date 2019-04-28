package com.huyingbao.module.wan.ui.article.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huyingbao.module.wan.R;
import com.huyingbao.module.wan.ui.article.model.Banner;

import java.util.List;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public class BannerAdapter extends BaseQuickAdapter<Banner, BaseViewHolder> {
    public BannerAdapter(@Nullable List<Banner> data) {
        super(R.layout.wan_recycle_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Banner item) {
        helper.setText(R.id.tv_item_name, item.getTitle())
                .setText(R.id.tv_item_description, item.getDesc())
                .setText(R.id.tv_item_id, "GithubId" + item.getId());
    }
}
