package com.huyingbao.module.git.ui.module;

import android.content.Context;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public interface GitActions {
    String TO_GIT_USER = "to_git_user";

    String GET_GIT_REPO_LIST = "get_git_repo_list";
    String GET_GIT_USER = "get_git_user";

    void getGitRepoList();

    void gitGitUser(Context context, int userId);
}
