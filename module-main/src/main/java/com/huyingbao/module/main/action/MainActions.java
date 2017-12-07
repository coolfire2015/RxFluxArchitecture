package com.huyingbao.module.main.action;

import android.content.Context;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public interface MainActions {
    String TO_PRODUCT_LIST = "to_product_list";
    String TO_SHOP = "to_shop";
    String TO_GITHUB = "to_github";

    String GET_PRODUCT_LIST = "get_product_list";
    String GET_SHOP = "get_shop";

    void getProductList();

    void getShop(Context context, int userId);
}
