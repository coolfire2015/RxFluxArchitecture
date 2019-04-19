package com.huyingbao.module.wan.kotlin.ui.article.action

/**
 * Created by liujunfeng on 2019/1/1.
 */
interface ArticleAction {

    /**
     * 获取文章列表
     *
     * @param page 页码
     */
    fun getArticleList(page: Int)

    /**
     * 获取Banner列表
     */
    fun getBannerList()

    companion object {
        const val TO_BANNER = "to_banner"
        const val TO_FRIEND = "to_friend"
        const val TO_LOGIN = "to_login"

        /**
         * 获取文章列表
         */
        const val GET_ARTICLE_LIST = "get_article_list"

        /**
         * 获取Banner列表
         */
        const val GET_BANNER_LIST = "get_banner_list"
    }
}
