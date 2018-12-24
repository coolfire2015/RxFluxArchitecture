package com.huyingbao.module.wan.ui.action;

import android.content.Context;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public interface WanAction {
    String TO_GIT_USER = "to_git_user";

    String GET_ARTICLE_LIST = "get_article_list";

    void getArticleList(int page);

    String GET_GIT_USER = "get_git_user";

    void gitGitUser(Context context, int userId);
}
