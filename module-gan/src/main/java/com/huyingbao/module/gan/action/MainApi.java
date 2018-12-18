package com.huyingbao.module.gan.action;

import com.huyingbao.module.gan.ui.main.model.Product;
import com.huyingbao.module.gan.ui.shop.model.Shop;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public interface MainApi {
    @GET("test/getProductList")
    Observable<ArrayList<Product>> getProductList();

    @GET("test/getShop/{shopId}")
    Observable<Shop> getShop(@Path("shopId") int shopId);
}
