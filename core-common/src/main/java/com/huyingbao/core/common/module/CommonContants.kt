package com.huyingbao.core.common.module

/**
 * 通用常量类
 *
 * Created by liujunfeng on 2019/5/30.
 */
class CommonContants {
    object Config {
        const val PAGE_SIZE = 20
        const val HTTP_TIME_OUT = 20L

    }

    object Key {
        const val ACCESS_TOKEN = "accessToken"
        const val USER_NAME = "userName"
        const val PASSWORD = "password"
        const val TITLE = "title"
        const val CONTENT = "content"
    }

    object Header {
        const val AUTHORIZATION = "Authorization"
    }
}
