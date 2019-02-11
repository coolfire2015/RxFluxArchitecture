package com.huyingbao.module.kotlin.ui.article.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huyingbao.module.kotlin.R
import com.huyingbao.module.kotlin.ui.article.model.Banner

/**
 * Created by liujunfeng on 2019/1/1.
 */
class BannerAdapter(data: List<Banner>?) : BaseQuickAdapter<Banner, BaseViewHolder>(R.layout.kotlin_recycle_item, data) {

    override fun convert(helper: BaseViewHolder, item: Banner) {
        helper.setText(R.id.tv_item_name, item.title)
                .setText(R.id.tv_item_description, item.desc)
                .setText(R.id.tv_item_id, "GithubId" + item.id)
    }
}
