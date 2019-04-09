package com.huyingbao.module.wan.ui.article.action

/**
 * Created by liujunfeng on 2019/1/1.
 */
interface ArticleAction {

    /**
     * ��ȡ�����б�
     *
     * @param page ҳ��
     */
    fun getArticleList(page: Int)

    /**
     * ��ȡBanner�б�
     */
    fun getBannerList()

    companion object {
        val TO_BANNER = "to_banner"
        val TO_FRIEND = "to_friend"
        val TO_LOGIN = "to_login"

        /**
         * ��ȡ�����б�
         */
        val GET_ARTICLE_LIST = "get_article_list"

        /**
         * ��ȡBanner�б�
         */
        val GET_BANNER_LIST = "get_banner_list"
    }
}
