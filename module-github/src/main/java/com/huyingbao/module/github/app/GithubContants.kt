package com.huyingbao.module.github.app

/**
 * Created by liujunfeng on 2019/4/3.
 */
class GithubContants {

    object Url {
        const val BASE_API = "https://api.github.com/"

        const val BASE_URL = "https://github.com/"

        const val BASE_CONTENT_URL = "https://raw.githubusercontent.com/"

        const val BASE_HOST = "https://ghchart.rshah.org/"

    }

    object Key {
        const val ACCESS_TOKEN = "accessToken"

        const val USER_NAME = "user_name"

        const val PASSWORD = "password"

        const val USER_INFO = "userInfo"
    }

    object Config {
        const val PAGE_SIZE = 30

        const val HTTP_TIME_OUT = 20L
    }
}
