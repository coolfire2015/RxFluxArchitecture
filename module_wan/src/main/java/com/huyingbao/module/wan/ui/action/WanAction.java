package com.huyingbao.module.wan.ui.action;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public interface WanAction {
    String GET_ARTICLE_LIST = "get_article_list";

    void getArticleList(int page);
}
