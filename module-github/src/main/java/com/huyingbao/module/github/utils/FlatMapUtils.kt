package com.huyingbao.module.github.utils

import io.reactivex.ObservableSource
import io.reactivex.Observer
import retrofit2.Response

/**
 * 将Response转化为实体数据
 */
class FlatMapResponse2Result<T>(private val response: Response<T>) : ObservableSource<T> {
    override fun subscribe(observer: Observer<in T?>) {
        if (response.isSuccessful) {
            //接口调用成功，向下传递body
            observer.onNext(response.body())
        } else {
            //接口调用失败，抛出异常
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