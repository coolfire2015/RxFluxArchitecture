package com.huyingbao.module.gan.ui.category.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huyingbao.module.gan.R;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public class CategoryAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public CategoryAdapter(@Nullable List<String> data) {
        super(R.layout.item_product, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_product_name, item);
    }
}
