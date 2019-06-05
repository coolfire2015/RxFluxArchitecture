package com.huyingbao.module.gan.ui.random.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huyingbao.core.image.ImageLoader;
import com.huyingbao.core.image.ImageLoaderUtils;
import com.huyingbao.module.gan.R;
import com.huyingbao.module.gan.ui.random.model.Product;

import java.util.List;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public class ProductAdapter extends BaseQuickAdapter<Product, BaseViewHolder> {
    public ProductAdapter(@Nullable List<Product> data) {
        super(R.layout.gan_recycle_item_product, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Product item) {
        ImageLoader.Builder imageLoader = new ImageLoader.Builder();
        imageLoader.isCircle = true;
        if (item.getImages() != null && item.getImages().size() > 0) {
            imageLoader.resource = item.getImages().get(0);
        } else {
            imageLoader.resource = item.getUrl();
        }
        imageLoader.errorHolder = android.R.drawable.ic_menu_camera;
        imageLoader.imgView = helper.getView(R.id.iv_product_img);
        ImageLoaderUtils.loadImage(mContext, imageLoader.build());
        helper.setText(R.id.tv_product_name, item.getDesc())
                .setText(R.id.tv_product_description, item.getCreatedAt())
                .setText(R.id.tv_product_id, "ProductId:" + item.getWho());
    }
}
