package com.huyingbao.module.wan.action;

import com.huyingbao.module.wan.ui.article.model.Article;
import com.huyingbao.module.wan.ui.article.model.Banner;
import com.huyingbao.module.wan.ui.article.model.Page;
import com.huyingbao.module.wan.ui.friend.model.WebSite;
import com.huyingbao.module.wan.ui.login.model.User;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author liujunfeng
 * @date 2019/1/1
 */
public interface WanApi {
    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<WanResponse<User>> login(@Field("username") String username,
                                        @Field("password") String password);

    /**
     * 登录
     *
     * @param username   用户名
     * @param password   密码
     * @param repassword 确认密码
     * @return
     */
    @FormUrlEncoded
    @POST("user/register")
    Observable<WanResponse<User>> register(@Field("username") String username,
                                           @Field("password") String password,
                                           @Field("repassword") String repassword);


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
