package com.huyingbao.module.wan.ui.friend.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huyingbao.module.wan.R;
import com.huyingbao.module.wan.ui.friend.model.WebSite;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author liujunfeng
 * @date 2019/1/1
 */
public class WebSiteAdapter extends BaseQuickAdapter<WebSite, BaseViewHolder> {
    public WebSiteAdapter(@Nullable List<WebSite> data) {
        super(R.layout.wan_recycle_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WebSite item) {
        helper.setText(R.id.tv_item_name, item.getName())
                .setText(R.id.tv_item_description, item.getLink())
                .setText(R.id.tv_item_id, "GithubId" + item.getId());
    }
}
