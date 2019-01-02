package com.huyingbao.module.wan.action;

import com.huyingbao.module.wan.ui.article.model.Article;
import com.huyingbao.module.wan.ui.article.model.Banner;
import com.huyingbao.module.wan.ui.article.model.Page;
import com.huyingbao.module.wan.ui.common.friend.model.WebSite;
import com.huyingbao.module.wan.ui.login.model.User;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public interface WanApi {
    @FormUrlEncoded
    @POST("user/login")
    Observable<WanResponse<User>> login(@Field("username") String username,
                                        @Field("password") String password);


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
