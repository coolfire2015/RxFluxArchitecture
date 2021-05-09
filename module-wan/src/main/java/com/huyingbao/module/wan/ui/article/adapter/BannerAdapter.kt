package com.huyingbao.module.wan.ui.article.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.huyingbao.module.wan.R
import com.huyingbao.module.wan.model.Banner

/**
 * Created by liujunfeng on 2019/1/1.
 */
class BannerAdapter(
        data: MutableList<Banner>?
) : BaseQuickAdapter<Banner, BaseViewHolder>(
        R.layout.wan_recycle_item_banner,
        data) {
    override fun convert(helper: BaseViewHolder, item: Banner) {
        helper.setText(R.id.tv_item_title, item.title)
    }
}
