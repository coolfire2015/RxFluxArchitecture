package com.huyingbao.module.wan.ui.friend.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huyingbao.module.wan.R
import com.huyingbao.module.wan.ui.friend.model.WebSite

/**
 * Created by liujunfeng on 2019/1/1.
 */
class WebSiteAdapter(data: List<WebSite>?) : BaseQuickAdapter<WebSite, BaseViewHolder>(R.layout.wan_recycle_item, data) {

    override fun convert(helper: BaseViewHolder, item: WebSite) {
        helper.setText(R.id.tv_item_name, item.name)
                .setText(R.id.tv_item_description, item.link)
                .setText(R.id.tv_item_id, "GithubId" + item.id)
    }
}
