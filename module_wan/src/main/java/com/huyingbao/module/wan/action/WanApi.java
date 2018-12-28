package com.huyingbao.module.wan.action;

import com.huyingbao.module.wan.ui.article.model.Article;
import com.huyingbao.module.wan.ui.article.model.Banner;
import com.huyingbao.module.wan.ui.article.model.Page;
import com.huyingbao.module.wan.ui.common.friend.model.WebSite;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public interface WanApi {
    @GET("friend/json")
    Observable<WanResponse<ArrayList<WebSite>>> getFriendList();

    @GET("banner/json")
    Observable<WanResponse<ArrayList<Banner>>> getBannerList();

    /**
     * 首页文章列表
     *
     * @param page
     * @return
     */
    @GET("article/list/{page}/json")
    Observable<WanResponse<Page<Article>>> getArticleList(@Path("page") int page);
}
