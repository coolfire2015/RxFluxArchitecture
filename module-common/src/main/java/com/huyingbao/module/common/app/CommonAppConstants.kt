package com.huyingbao.module.common.app

import com.huyingbao.core.arch.RxApp
import com.huyingbao.module.common.R
import java.util.*

/**
 * 通用常量类
 *
 * Created by liujunfeng on 2019/5/30.
 */
class CommonAppConstants {
    /**
     * 配置
     */
    object Config {
        /**
         * 网络连接超时事件
         */
        const val HTTP_TIME_OUT = 20L
    }

    /**
     * Key值
     */
    object Key {
        /**
         * 页码
         */
        const val PAGE = "page"
        /**
         * 标题
         */
        const val TITLE = "title"
        /**
         * Url地址
         */
        const val URL = "url"
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

        const val WebActivity = "/common/WebActivity"
        const val FirstActivity = "/first/FirstActivity"
        const val SecondActivity = "/second/SecondActivity"
        const val ThirdActivity = "/third/ThirdActivity"


        init {
            RxApp.application?.let {
                routerMap[it.getString(R.string.app_label_common)] = WebActivity
                routerMap[it.getString(R.string.app_label_app)] = FirstActivity
                routerMap[it.getString(R.string.app_label_first)] = FirstActivity
                routerMap[it.getString(R.string.app_label_second)] = SecondActivity
                routerMap[it.getString(R.string.app_label_third)] = ThirdActivity
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
