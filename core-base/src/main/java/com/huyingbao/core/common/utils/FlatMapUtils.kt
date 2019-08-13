package com.huyingbao.core.common.utils

import com.huyingbao.core.common.model.CommonException
import com.huyingbao.core.common.module.CommonContants

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.functions.Function
import retrofit2.Response

/**
 * FlatMap转化工具类
 *
 * Created by liujunfeng on 2019/1/1.
 */
object FlatMapUtils {
    /**
     * 验证接口返回数据是正常，取出封装的
     */
    fun <T, R> verifyResponse(): Function<T, Observable<R>> {
        return Function {
            if (it is Response<*>) {
                if (it.isSuccessful) {
                    //接口调用成功，向下传递body
                    return@Function Observable.just((it as Response<R>).body())
                } else {
                    //接口调用失败，向下传递异常
                    return@Function Observable.error(CommonException(it.code(), it.errorBody()?.string()))
                }
            } else {
                //不是Response，传递自定义异常
                return@Function Observable.error(CommonException(CommonContants.Error.COMMON, "返回数据异常:${it.toString()}"))
            }
        }
    }
}

/**
 * 将Response转化为实体数据
 */
class FlatMapResponse2Result<T>(private val response: Response<T>) : ObservableSource<T> {
    override fun subscribe(observer: Observer<in T?>) {
        if (response.isSuccessful) {
            //接口调用成功，向下传递body
            observer.onNext(response.body())
        } else {
            //接口调用失败，向下传递异常
            observer.onError(Throwable(response.code().toString(), Throwable(response.errorBody().toString())))
        }
    }
}

/**
 * 将实体数据转化为Response
 */
class FlatMapResult2Response<T>(private val t: T) : ObservableSource<Response<T>> {
    override fun subscribe(observer: Observer<in Response<T>?>) {
        observer.onNext(Response.success(t))
    }
}
