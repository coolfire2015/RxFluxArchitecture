package com.huyingbao.module.wan.ui.article.action

/**
 * @author liujunfeng
 * @date 2019/1/1
 */
interface ArticleAction {

    fun getArticleList(page: Int)

    fun getBannerList()

    companion object {
        val TO_BANNER = "to_banner"
        val TO_FRIEND = "to_friend"
        val TO_LOGIN = "to_login"

        val GET_ARTICLE_LIST = "get_article_list"

        val GET_BANNER_LIST = "get_banner_list"
    }
}
