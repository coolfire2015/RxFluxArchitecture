package com.huyingbao.core.common.utils;

import com.huyingbao.core.common.model.CommonException;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import retrofit2.Response;

/**
 * FlatMap转化工具类
 * <p>
 * Created by liujunfeng on 2019/1/1.
 */
public class FlatMapUtils {
    /**
     * 验证接口返回数据是正常，取出封装的
     */
    @SuppressWarnings("unchecked")
    public static <T, R> Function<T, Observable<R>> verifyResponse() {
        return response -> {
            try {
                //不是Response，传递自定义异常
                if (!(response instanceof Response)) {
                    return Observable.error(new CommonException(600, "返回数据异常:" + response.toString()));
                }
                //接口调用成功，向下传递body
                if (((Response<R>) response).isSuccessful()) {
                    return Observable.just(((Response<R>) response).body());
                }
                //传递异常
                String errorMessage = ((Response) response).errorBody() != null ? ((Response) response).errorBody().string() : "未知异常！";
                CommonException exception = new CommonException(((Response) response).code(), errorMessage);
                return Observable.error(exception);
            } catch (Exception e) {
                return Observable.error(e);
            }
        };
    }
}
