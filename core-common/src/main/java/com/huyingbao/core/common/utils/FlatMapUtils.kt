package com.huyingbao.core.common.utils

import com.huyingbao.core.common.model.CommonException
import com.huyingbao.core.common.module.CommonContants

import io.reactivex.Observable
import io.reactivex.functions.Function
import retrofit2.Response

/**
 * FlatMap转化工具类
 *
 *
 * Created by liujunfeng on 2019/1/1.
 */
object FlatMapUtils {
    /**
     * 验证接口返回数据是正常，取出封装的
     */
    fun <T, R> verifyResponse(): Function<T, Observable<R>> {
        return Function{
            if (it is Response<*>) {
                if (it.isSuccessful) {
                    //接口调用成功，向下传递body
                    return@Function Observable.just((it as Response<R>).body())
                } else {
                    //接口调用失败，向下传递异常
                    throw CommonException(it.code(), it.errorBody()?.string() ?: "未知异常！")
                }
            } else {
                //不是Response，传递自定义异常
                throw CommonException(CommonContants.Error.COMMON, "返回数据异常:" + it.toString())
            }
        }
    }
}
