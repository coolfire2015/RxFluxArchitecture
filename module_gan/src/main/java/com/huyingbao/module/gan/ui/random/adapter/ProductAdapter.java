package com.huyingbao.module.gan.ui.random.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huyingbao.module.gan.R;
import com.huyingbao.module.gan.ui.random.model.Product;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author liujunfeng
 * @date 2019/1/1
 */
public class ProductAdapter extends BaseQuickAdapter<Product, BaseViewHolder> {
    public ProductAdapter(@Nullable List<Product> data) {
        super(R.layout.gan_recycle_item_product, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Product item) {
        helper.setText(R.id.tv_product_name, item.getDesc())
                .setText(R.id.tv_product_description, item.getCreatedAt())
                .setText(R.id.tv_product_id, "ProductId:" + item.getWho());
    }
}
