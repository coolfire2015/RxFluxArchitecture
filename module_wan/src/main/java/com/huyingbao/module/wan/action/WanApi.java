package com.huyingbao.module.wan.action;

import com.huyingbao.module.wan.ui.model.Article;
import com.huyingbao.module.wan.ui.model.GitUser;
import com.huyingbao.module.wan.ui.model.WanPage;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public interface WanApi {
    /**
     * 首页文章列表
     *
     * @param page
     * @return
     */
    @GET("article/list/{page}/json")
    Observable<WanResponse<WanPage<Article>>> getArticleList(@Path("page") int page);

    @GET("users/{id}")
    Observable<GitUser> getGitUser(@Path("id") int shopId);
}
