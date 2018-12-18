package com.huyingbao.module.gan.action;

import com.huyingbao.module.gan.ui.random.model.Product;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public interface MainApi {
    @GET("random/data/福利/2000 ")
    Observable<GanResponse<Product>> getProductList();
}
