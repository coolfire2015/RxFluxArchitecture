package com.huyingbao.module.common.app

import com.huyingbao.core.arch.FluxApp
import com.huyingbao.module.common.R
import java.util.*

/**
 * 通用常量类
 *
 * Created by liujunfeng on 2019/5/30.
 */
class CommonAppConstants {
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

        /**
         * 访问令牌
         */
        const val ACCESS_TOKEN = "accessToken"

        /**
         * 用户名
         */
        const val USER_NAME = "username"

        /**
         * 密码
         */
        const val PASSWORD = "password"

        /**
         * 标题
         */
        const val TITLE = "title"

        /**
         * 内容
         */
        const val CONTENT = "content"

        /**
         * Url地址
         */
        const val URL = "url"

        /**
         * 本地路径
         */
        const val FILE_PATH = "filepath"

        /**
         * 索引
         */
        const val INDEX = "index"

        /**
         * 类别
         */
        const val CATEGORY = "category"

        /**
         * 个数
         */
        const val COUNT = "count"

        /**
         * 页码
         */
        const val PAGE = "page"

        /**
         * true：因登录失效，跳转LoginActivity，需要跳转LoginFragment
         */
        const val TO_LOGIN: String = "toLogin"
    }

    object Header {
        /**
         * 表头Auth认证
         */
        const val AUTHORIZATION = "Authorization"
    }

    /**
     * 全局路由地址静态类
     *
     * 可以根据module manifest文件中 Application label 值提供不同的路由地址
     */
    object Router {
        /**
         * key   ：module manifest文件中 Application label值
         * value ：module 需要启动的第一个Activity路由地址
         */
        private val routerMap: MutableMap<String, String> = HashMap()

        const val RandomActivity = "/gan/random/RandomActivity"

        const val ArticleActivity = "/wan/article/ArticleActivity"

        const val StartActivity = "/common/start/StartActivity"
        const val WebActivity = "/common/web/WebActivity"

        const val LoginActivity = "/github/login/LoginActivity"
        const val PersonActivity = "/github/person/PersonActivity"
        const val IssueActivity = "/github/issue/IssueActivity"
        const val CodeActivity = "/github/code/CodeActivity"
        const val ReposActivity = "/github/repos/ReposActivity"
        const val MainActivity = "/github/main/MainActivity"
        const val UserActivity = "/github/user/UserActivity"
        const val StarActivity = "/github/star/StarActivity"
        const val SearchActivity = "/github/search/SearchActivity"

        const val NcovMainActivity = "/ncov/main/MainActivity"

        init {
            FluxApp.application?.let {
                routerMap[it.getString(R.string.app_label_app)] = LoginActivity
                routerMap[it.getString(R.string.app_label_common)] = WebActivity
                routerMap[it.getString(R.string.app_label_github)] = LoginActivity
                routerMap[it.getString(R.string.app_label_wan)] = ArticleActivity
                routerMap[it.getString(R.string.app_label_gan)] = RandomActivity
                routerMap[it.getString(R.string.app_label_ncov)] = NcovMainActivity
            }
        }

        /**
         * 根据不同module manifest文件中 Application label值，获取对应启动Activity路由地址
         */
        fun getAppRouter(label: String?): String {
            return routerMap[label] ?: WebActivity
        }
    }
}
