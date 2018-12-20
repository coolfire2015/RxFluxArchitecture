package com.huyingbao.module.git.action;

import com.huyingbao.module.git.ui.model.GitRepo;
import com.huyingbao.module.git.ui.model.GitUser;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public interface GitApi {
    @GET("repositories")
    Observable<ArrayList<GitRepo>> getGitRepoList();

    @GET("users/{id}")
    Observable<GitUser> getGitUser(@Path("id") int shopId);
}
