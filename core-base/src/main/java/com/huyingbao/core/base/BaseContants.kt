package com.huyingbao.core.base

/**
 * 通用常量类
 *
 * Created by liujunfeng on 2019/5/30.
 */
class BaseContants {
    object Config {
        /**
         * 每页数据条数
         */
        const val PAGE_SIZE = 20
        /**
         * 网络连接超时事件
         */
        const val HTTP_TIME_OUT = 20L
    }

    object Key {
        /**
         * 黑夜模式
         *
         * [androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO]
         *
         * [androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES]
         *
         * [androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM]
         *
         * [androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_AUTO_TIME]
         *
         * [androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY]
         *
         */
        const val NIGHT_MODE = "nightMode"
        const val ACCESS_TOKEN = "accessToken"
        const val USER_NAME = "userName"
        const val PASSWORD = "password"
        const val TITLE = "title"
        const val CONTENT = "content"
        const val INFO = "info"
        const val URL = "url"
        /**
         * 索引
         */
        const val INDEX = "index"
    }

    object Header {
        const val AUTHORIZATION = "Authorization"
    }

    object Error {
        const val COMMON = 600
        const val UNAUTHORIZED = 401
    }
}
