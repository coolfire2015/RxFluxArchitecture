package com.huyingbao.core.progress;

import android.text.TextUtils;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 转换ResponseBody为ProgressResponseBody
 * Created by liujunfeng on 2019/4/10.
 */
@Singleton
public class ProgressInterceptor implements Interceptor {
    @Inject
    public ProgressInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (response.body() == null) {
            return response;
        }
        //获取Header传递过来的参数,并传递到ProgressResponseBody对象中
        String tag = null;
        if (!TextUtils.isEmpty(response.request().header(RxProgress.TAG))) {
            tag = response.request().header(RxProgress.TAG);
        }
        ProgressResponseBody body = new ProgressResponseBody(response.body(), tag);
        return response.newBuilder().body(body).build();
    }
}
