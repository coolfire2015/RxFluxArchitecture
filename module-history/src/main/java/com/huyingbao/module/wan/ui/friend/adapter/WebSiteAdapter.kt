package com.huyingbao.module.history.ui.friend.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.huyingbao.module.history.R
import com.huyingbao.module.history.model.WebSite

/**
 * Created by liujunfeng on 2019/1/1.
 */
class WebSiteAdapter(
    data: MutableList<WebSite>?
) : BaseQuickAdapter<WebSite, BaseViewHolder>(
    R.layout.history_recycle_item_friend,
    data
) {
    override fun convert(helper: BaseViewHolder, item: WebSite) {
        helper.setText(R.id.tv_item_title, item.name)
    }
}
