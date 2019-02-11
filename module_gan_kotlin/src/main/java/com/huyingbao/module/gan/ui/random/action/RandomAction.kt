package com.huyingbao.module.gan.ui.random.action

/**
 * Created by liujunfeng on 2019/1/1.
 */
interface RandomAction {

    fun getProductList(category: String, count: Int, page: Int)

    companion object {
        val TO_SHOW_DATA = "to_show_data"

        val GET_PRODUCT_LIST = "get_product_list"
    }
}
