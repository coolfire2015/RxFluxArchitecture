package com.huyingbao.module.wan.ui.article.action

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
        val TO_BANNER = "to_banner"
        val TO_FRIEND = "to_friend"
        val TO_LOGIN = "to_login"

        /**
         * 获取文章列表
         */
        val GET_ARTICLE_LIST = "get_article_list"

        /**
         * 获取Banner列表
         */
        val GET_BANNER_LIST = "get_banner_list"
    }
}
