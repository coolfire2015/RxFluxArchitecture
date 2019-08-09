package com.huyingbao.module.common.app

import com.huyingbao.core.arch.RxApp
import com.huyingbao.module.common.R
import java.util.*

/**
 * 全局路由地址静态类，可以根据module manifest文件中 Application label 值提供不同的路由地址
 */
object CommonRouter {
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

    init {
        RxApp.application?.let {
            routerMap[it.getString(R.string.app_label_app)] = LoginActivity
            routerMap[it.getString(R.string.app_label_common)] = LoginActivity
            routerMap[it.getString(R.string.app_label_github)] = LoginActivity
            routerMap[it.getString(R.string.app_label_wan)] = ArticleActivity
            routerMap[it.getString(R.string.app_label_gan)] = RandomActivity
        }
    }

    /**
     * 根据不同module manifest文件中 Application label值，获取对应启动Activity路由地址
     */
    fun getAppRouter(label: String?): String {
        return routerMap[label] ?: LoginActivity
    }
}


