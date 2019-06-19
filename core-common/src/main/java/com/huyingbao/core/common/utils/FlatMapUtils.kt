package com.huyingbao.core.common.utils

import com.huyingbao.core.common.model.CommonException

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
        return Function { response ->
            try {
                //不是Response，传递自定义异常
                if (response !is Response<*>) {
                    return@Function Observable.error<R>(CommonException(600, "返回数据异常:" + response.toString()))
                }
                //接口调用成功，向下传递body
                if ((response as Response<R>).isSuccessful) {
                    return@Function Observable.just((response as Response<R>).body()!!)
                }
                //传递异常
                val errorMessage = if ((response as Response<*>).errorBody() != null) (response as Response<*>).errorBody()!!.string() else "未知异常！"
                val exception = CommonException((response as Response<*>).code(), errorMessage)
                return@Function Observable.error<R>(exception)
            } catch (e: Exception) {
                return@Function Observable.error<R>(e)
            }
        }
    }
}
