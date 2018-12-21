package com.huyingbao.module.gan.action;

import com.huyingbao.module.gan.ui.random.model.Product;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public interface GanApi {
    @GET("data/{category}/{count}/{page} ")
    Observable<GanResponse<Product>> getDataList(
            @Path("category") String category,
            @Path("count") int count,
            @Path("page") int page);
}
