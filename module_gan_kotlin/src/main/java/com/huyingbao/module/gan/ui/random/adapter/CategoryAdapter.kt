package com.huyingbao.module.gan.ui.random.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huyingbao.module.gan.R

/**
 * Created by liujunfeng on 2019/1/1.
 */
class CategoryAdapter(data: List<String>?) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.gan_recycle_item_product, data) {

    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.tv_product_name, item)
    }
}
