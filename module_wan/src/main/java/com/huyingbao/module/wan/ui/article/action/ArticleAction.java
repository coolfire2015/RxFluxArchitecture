package com.huyingbao.module.wan.ui.article.action;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public interface ArticleAction {
    String GET_ARTICLE_LIST = "get_article_list";

    void getArticleList(int page);

    String GET_BANNER_LIST = "get_banner_list";

    void getBannerList();
}
