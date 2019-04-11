package com.huyingbao.core.progress;

import android.text.TextUtils;

import com.huyingbao.core.common.action.CommonActionCreator;

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
    private CommonActionCreator mCommonActionCreator;

    @Inject
    public ProgressInterceptor(CommonActionCreator commonActionCreator) {
        mCommonActionCreator = commonActionCreator;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (response.body() == null) {
            return response;
        }
        //获取Header传递过来的参数,并传递到ProgressResponseBody对象中
        String tag = null;
        if (!TextUtils.isEmpty(response.request().header(ProgressInfo.TAG))) {
            tag = response.request().header(ProgressInfo.TAG);
        }
        ProgressResponseBody body = new ProgressResponseBody(response.body(), mCommonActionCreator, tag);
        return response.newBuilder().body(body).build();
    }
}
