package com.huyingbao.module.gan.action

import com.huyingbao.module.gan.ui.random.model.Product

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by liujunfeng on 2019/1/1.
 */
interface GanApi {
    @GET("data/{category}/{count}/{page} ")
    fun getDataList(
            @Path("category") category: String,
            @Path("count") count: Int,
            @Path("page") page: Int): Observable<GanResponse<Product>>
}
