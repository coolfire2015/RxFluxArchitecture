package com.huyingbao.module.kotlin.action

import com.huyingbao.module.kotlin.ui.article.model.Article
import com.huyingbao.module.kotlin.ui.article.model.Banner
import com.huyingbao.module.kotlin.ui.article.model.Page
import com.huyingbao.module.kotlin.ui.friend.model.WebSite
import com.huyingbao.module.kotlin.ui.login.model.User
import io.reactivex.Observable
import retrofit2.http.*
import java.util.*

/**
 * Created by liujunfeng on 2019/1/1.
 */
interface KotlinApi {
    @get:GET("friend/json")
    val friendList: Observable<KotlinResponse<ArrayList<WebSite>>>

    @get:GET("banner/json")
    val bannerList: Observable<KotlinResponse<ArrayList<Banner>>>

    @FormUrlEncoded
    @POST("user/login")
    fun login(@Field("username") username: String,
              @Field("password") password: String): Observable<KotlinResponse<User>>

    /**
     * 首页文章列表
     *
     * @param page
     * @return
     */
    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page: Int): Observable<KotlinResponse<Page<Article>>>
}
