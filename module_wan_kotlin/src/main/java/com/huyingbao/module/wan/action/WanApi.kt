package com.huyingbao.module.wan.action

import com.huyingbao.module.wan.ui.article.model.Article
import com.huyingbao.module.wan.ui.article.model.Banner
import com.huyingbao.module.wan.ui.article.model.Page
import com.huyingbao.module.wan.ui.friend.model.WebSite
import com.huyingbao.module.wan.ui.login.model.User
import io.reactivex.Observable
import retrofit2.http.*
import java.util.*

/**
 * @author liujunfeng
 * @date 2019/1/1
 */
interface WanApi {


    @get:GET("friend/json")
    val friendList: Observable<WanResponse<ArrayList<WebSite>>>

    @get:GET("banner/json")
    val bannerList: Observable<WanResponse<ArrayList<Banner>>>

    @FormUrlEncoded
    @POST("user/login")
    fun login(@Field("username") username: String,
              @Field("password") password: String): Observable<WanResponse<User>>

    /**
     * 首页文章列表
     *
     * @param page
     * @return
     */
    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page: Int): Observable<WanResponse<Page<Article>>>
}
