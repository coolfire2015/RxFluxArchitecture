package com.huyingbao.module.gan.action;

import com.huyingbao.module.gan.ui.random.model.Product;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author liujunfeng
 * @date 2019/1/1
 */
public interface GanApi {
    /**
     * 获取文章数据列表
     *
     * @param category 类别
     * @param count    数目
     * @param page     页码
     * @return
     */
    @GET("data/{category}/{count}/{page} ")
    Observable<GanResponse<Product>> getDataList(
            @Path("category") String category,
            @Path("count") int count,
            @Path("page") int page);
}
