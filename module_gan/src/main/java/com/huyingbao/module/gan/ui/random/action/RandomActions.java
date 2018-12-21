package com.huyingbao.module.gan.ui.random.action;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public interface RandomActions {
    String GET_PRODUCT_LIST = "get_product_list";

    void getProductList(String category, int count, int page);
}
