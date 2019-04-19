package com.huyingbao.module.wan.kotlin.action

import com.huyingbao.module.wan.kotlin.ui.article.model.Article
import com.huyingbao.module.wan.kotlin.ui.article.model.Banner
import com.huyingbao.module.wan.kotlin.ui.article.model.Page
import com.huyingbao.module.wan.kotlin.ui.friend.model.WebSite
import com.huyingbao.module.wan.kotlin.ui.login.model.User
import io.reactivex.Observable
import retrofit2.http.*
import java.util.*

/**
 * Created by liujunfeng on 2019/1/1.
 */
interface WanApi {


    /**
     * 获取友站列表
     *
     * @return
     */
    @get:GET("friend/json")
    val friendList: Observable<WanResponse<ArrayList<WebSite>>>

    /**
     * 获取Banner列表
     *
     * @return
     */
    @get:GET("banner/json")
    val bannerList: Observable<WanResponse<ArrayList<Banner>>>

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    fun login(@Field("username") username: String,
              @Field("password") password: String): Observable<WanResponse<User>>

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
    fun register(@Field("username") username: String,
                 @Field("password") password: String,
                 @Field("repassword") repassword: String): Observable<WanResponse<User>>

    /**
     * 获取文章列表
     *
     * @param page 页码
     * @return
     */
    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page: Int): Observable<WanResponse<Page<Article>>>
}
