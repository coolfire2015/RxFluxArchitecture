package com.huyingbao.module.gan.ui.random.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.huyingbao.module.gan.R
import com.huyingbao.module.gan.ui.random.model.Product

/**
 * Created by liujunfeng on 2019/1/1.
 */
class ProductAdapter(data: List<Product>?) : BaseQuickAdapter<Product, BaseViewHolder>(R.layout.gan_recycle_item_product, data) {

    override fun convert(helper: BaseViewHolder, item: Product) {
        helper.setText(R.id.tv_product_name, item.desc)
                .setText(R.id.tv_product_description, item.createdAt)
                .setText(R.id.tv_product_id, "ProductId:" + item.who!!)
    }
}
