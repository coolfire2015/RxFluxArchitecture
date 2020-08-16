package com.huyingbao.module.wan.ui.article.model

/**
 * Created by liujunfeng on 2019/1/1.
 */
class WanResponse<T> {
    var data: T? = null
    var errorCode: Int = 0
    var errorMsg: String? = null
}
