package com.huyingbao.module.wan.ui.article.action

import com.huyingbao.module.wan.model.Article
import com.huyingbao.module.wan.model.Banner
import com.huyingbao.module.wan.model.Page
import com.huyingbao.module.wan.model.WanResponse
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*

/**
 * Created by liujunfeng on 2019/1/1.
 */
interface ArticleAction {

    companion object {
        const val TO_BANNER = "toBanner"
        const val TO_FRIEND = "toFriend"

        /**
         * 获取文章列表
         */
        const val GET_ARTICLE_LIST = "getArticleList"

        /**
         * 获取Banner列表
         */
        const val GET_BANNER_LIST = "getBannerList"
    }

    /**
     * 获取文章列表
     *
     * @param page 页码
     */
    fun getArticleList(page: Int)

    /**
     * 获取Banner列表
     */
    fun getBannerList()
}

interface ArticleApi {
    /**
     * 获取文章列表
     *
     * @param page 页码
     */
    @GET("article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): WanResponse<Page<Article>>

    /**
     * 获取Banner列表
     */
    @GET("banner/json")
    suspend fun getBannerList(): WanResponse<ArrayList<Banner>>
}
