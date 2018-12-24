package com.huyingbao.module.wan.action;

import com.huyingbao.module.wan.ui.model.GitRepo;
import com.huyingbao.module.wan.ui.model.GitUser;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public interface WanApi {
    @GET("repositories")
    Observable<ArrayList<GitRepo>> getGitRepoList();

    @GET("users/{id}")
    Observable<GitUser> getGitUser(@Path("id") int shopId);
}
