package com.huyingbao.module.wan.ui.article.action;

/**
 * @author liujunfeng
 * @date 2019/1/1
 */
public interface ArticleAction {
    String TO_BANNER = "to_banner";
    String TO_FRIEND = "to_friend";
    String TO_LOGIN = "to_login";

    /**
     * 获取文章列表
     */
    String GET_ARTICLE_LIST = "article/list";

    /**
     * 获取文章列表
     *
     * @param page 页码
     */
    void getArticleList(int page);

    /**
     * 获取Banner列表
     */
    String GET_BANNER_LIST = "get_banner_list";

    /**
     * 获取Banner列表
     */
    void getBannerList();
}
