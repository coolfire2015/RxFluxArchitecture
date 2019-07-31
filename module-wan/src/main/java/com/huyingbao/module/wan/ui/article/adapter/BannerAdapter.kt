package com.huyingbao.module.wan.ui.article.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huyingbao.module.wan.R
import com.huyingbao.module.wan.ui.article.model.Banner

/**
 * Created by liujunfeng on 2019/1/1.
 */
class BannerAdapter(data: List<Banner>?) : BaseQuickAdapter<Banner, BaseViewHolder>(R.layout.wan_recycle_item, data) {
    override fun convert(helper: BaseViewHolder, item: Banner) {
        helper.setText(R.id.tv_item_title, item.title)
    }
}
