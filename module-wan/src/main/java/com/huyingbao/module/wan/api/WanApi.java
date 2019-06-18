package com.huyingbao.module.wan.api;

import com.huyingbao.module.wan.app.WanResponse;
import com.huyingbao.module.wan.ui.article.model.Article;
import com.huyingbao.module.wan.ui.article.model.Banner;
import com.huyingbao.module.wan.ui.article.model.Page;
import com.huyingbao.module.wan.ui.friend.model.WebSite;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public interface WanApi {
    /**
     * 获取友站列表
     *
     * @return
     */
    @GET("friend/json")
    Observable<WanResponse<ArrayList<WebSite>>> getFriendList();

    /**
     * 获取Banner列表
     *
     * @return
     */
    @GET("banner/json")
    Observable<WanResponse<ArrayList<Banner>>> getBannerList();

    /**
     * 获取文章列表
     *
     * @param page 页码
     * @return
     */
    @GET("article/list/{page}/json")
    Observable<WanResponse<Page<Article>>> getArticleList(@Path("page") int page);
}
