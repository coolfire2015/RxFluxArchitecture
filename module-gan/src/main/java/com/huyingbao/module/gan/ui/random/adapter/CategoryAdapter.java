package com.huyingbao.module.gan.ui.random.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huyingbao.module.gan.R;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public class CategoryAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public CategoryAdapter(@Nullable List<String> data) {
        super(R.layout.gan_recycle_item_product, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_product_name, item);
    }
}
