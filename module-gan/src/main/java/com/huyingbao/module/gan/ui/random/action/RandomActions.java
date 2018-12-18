package com.huyingbao.module.gan.ui.random.action;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public interface RandomActions {
    String TO_PRODUCT_LIST = "to_product_list";
    String TO_SHOP = "to_shop";
    String TO_GITHUB = "to_github";

    String GET_PRODUCT_LIST = "get_product_list";

    void getProductList(int page);
}
