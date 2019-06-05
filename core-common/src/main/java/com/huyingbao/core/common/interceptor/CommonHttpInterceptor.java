package com.huyingbao.core.common.interceptor;

import android.text.TextUtils;

import com.huyingbao.core.common.BuildConfig;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
public class CommonHttpInterceptor implements Interceptor {
    private volatile String mBaseUrl;

    @Inject
    public CommonHttpInterceptor() {
    }

    public void setBaseUrl(String baseUrl) {
        mBaseUrl = baseUrl;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        Request.Builder builder = oldRequest.newBuilder();
        builder.addHeader("Content-Type", "application/json;charset=UTF-8")
                .addHeader("Accept", "application/json;charset=UTF-8")
                .addHeader("X-Requested-With", "XMLHttpRequest");
        //设置hostUrl地址
        if (!TextUtils.isEmpty(mBaseUrl)) {
            HttpUrl newHttpUrl = HttpUrl.parse(mBaseUrl);
            builder.url(oldRequest.url().newBuilder()
                    .scheme(newHttpUrl.scheme())
                    .host(newHttpUrl.host())
                    .port(newHttpUrl.port())
                    .build());
        }
        //创建Request
        Request request = builder.build();
        //发起请求时间String.format("发送请求 %s", response.request().url())
        long t1 = System.nanoTime();
        //调用接口,返回数据
        Response response = chain.proceed(request);
        // 不打印日志并且数据正常直接返回
        if (!BuildConfig.DEBUG) {
            return response;
        }
        String content = response.body().string();
        long t2 = System.nanoTime();
        Logger.i(String.format("接收 for %s in %.1fms", response.request().url(), (t2 - t1) / 1e6d));
        Logger.json(content);
        return response.newBuilder().body(ResponseBody.create(response.body().contentType(), content)).build();
    }
}
