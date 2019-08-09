package com.huyingbao.module.common.app

import com.huyingbao.core.arch.RxApp
import com.huyingbao.module.common.R
import java.util.*

object CommonAppStore {
    private val activityRouterMap: MutableMap<String, String> = HashMap()

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
            activityRouterMap[it.getString(R.string.app_label_app)] = LoginActivity
            activityRouterMap[it.getString(R.string.app_label_common)] = LoginActivity
            activityRouterMap[it.getString(R.string.app_label_github)] = LoginActivity
            activityRouterMap[it.getString(R.string.app_label_wan)] = ArticleActivity
            activityRouterMap[it.getString(R.string.app_label_gan)] = RandomActivity
        }
    }

    fun getAppRouter(label: String?): String {
        return activityRouterMap[label] ?: LoginActivity
    }
}


