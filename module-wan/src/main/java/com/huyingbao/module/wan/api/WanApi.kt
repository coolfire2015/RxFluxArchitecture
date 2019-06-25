package com.huyingbao.module.wan.api

import com.huyingbao.module.wan.app.WanResponse
import com.huyingbao.module.wan.ui.article.model.Article
import com.huyingbao.module.wan.ui.article.model.Banner
import com.huyingbao.module.wan.ui.article.model.Page
import com.huyingbao.module.wan.ui.friend.model.WebSite
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*

/**
 * Created by liujunfeng on 2019/1/1.
 */
interface WanApi {
    /**
     * 获取友站列表
     */
    @get:GET("friend/json")
    val friendList: Observable<WanResponse<ArrayList<WebSite>>>

    /**
     * 获取Banner列表
     */
    @get:GET("banner/json")
    val bannerList: Observable<WanResponse<ArrayList<Banner>>>

    /**
     * 获取文章列表
     *
     * @param page 页码
     */
    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page: Int): Observable<WanResponse<Page<Article>>>
}
