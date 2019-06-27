package com.huyingbao.core.arch.action

import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.*
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * 实现交互方法，创建[RxAction]，直接发送给[com.huyingbao.core.arch.store.RxStore]。
 *
 * 创建[RxChange]、[RxLoading]、[RxError]、[RxRetry]，直接发送给[com.huyingbao.core.arch.view.RxFluxView]。
 *
 * 类似MVP模式中的Presenter，只是方法执行完成没有回调方法。
 *
 * Created by liujunfeng on 2019/1/1.
 */
abstract class RxActionCreator(
        private val rxDispatcher: RxDispatcher,
        private val rxActionManager: RxActionManager) {

    /**
     * IO线程运行被观察者[Observable]方法， 主线程观察者[io.reactivex.Observer]接收并封装方法结果到[RxAction]。
     *
     * 关联[RxAction]与[Disposable]。
     *
     * @param canShowLoading true:有进度显示,false:无进度显示
     * @param canRetry       true:操作异常可重试,false:操作异常抛异常
     */
    private fun <T> postRxAction(rxAction: RxAction, httpObservable: Observable<T>, canShowLoading: Boolean, canRetry: Boolean) {
        if (hasRxAction(rxAction)) {
            return
        }
        httpObservable
                //指定IO线程
                .subscribeOn(Schedulers.io())
                // 调用开始
                .doOnSubscribe {
                    if (canShowLoading) {
                        //发送RxLoading(显示)事件
                        postRxLoading(rxAction.tag, true)
                    }
                }
                // 调用结束
                .doFinally {
                    if (canShowLoading) {
                        //发送RxLoading(消失)事件
                        postRxLoading(rxAction.tag, false)
                    }
                }
                // 操作结束,在io线程中接收接收反馈,没有切换线程
                .subscribe(object : Observer<T> {
                    override fun onComplete() {
                        //操作结束
                        removeRxAction(rxAction)
                    }

                    override fun onSubscribe(d: Disposable) {
                        //关联操作
                        addRxAction(rxAction, d)
                    }

                    override fun onNext(t: T) {
                        //操作进行中
                        rxAction.setResponse(t)
                        postRxAction(rxAction)
                    }

                    override fun onError(e: Throwable) {
                        //操作异常
                        if (canRetry) {
                            postRxRetry(rxAction.tag, httpObservable, e)
                        } else {
                            postRxError(rxAction.tag, e)
                        }
                        removeRxAction(rxAction)
                    }
                })
    }

    /**
     * [RxActionManager]是否已存在[RxAction]
     */
    protected fun hasRxAction(rxAction: RxAction): Boolean {
        return rxActionManager.contains(rxAction)
    }

    /**
     * [RxActionManager]添加[RxAction]和[Disposable]
     */
    protected fun addRxAction(rxAction: RxAction, disposable: Disposable) {
        rxActionManager.add(rxAction, disposable)
    }

    /**
     * [RxActionManager]移除[RxAction]，停止对应的[Disposable]，
     * 被观察者[Observable]正在运行的方法会被停止。
     */
    protected fun removeRxAction(rxAction: RxAction) {
        rxActionManager.remove(rxAction)
    }

    /**
     * [RxDispatcher]发送[RxAction]
     */
    protected fun postRxAction(rxAction: RxAction) {
        rxDispatcher.postRxAction(rxAction)
    }

    /**
     * [RxDispatcher]发送[RxLoading]
     *
     * @param isLoading true:显示，false:消失
     */
    protected fun postRxLoading(tag: String, isLoading: Boolean) {
        rxDispatcher.postRxLoading(RxLoading.newInstance(tag, isLoading))
    }

    /**
     * [RxDispatcher]发送[RxRetry]
     */
    protected fun <T> postRxRetry(tag: String, httpObservable: Observable<T>, throwable: Throwable) {
        rxDispatcher.postRxRetry(RxRetry.newInstance(tag, throwable, httpObservable))
    }

    /**
     * [RxDispatcher]发送[RxError]
     */
    protected fun postRxError(tag: String, throwable: Throwable) {
        rxDispatcher.postRxError(RxError.newInstance(tag, throwable))
    }

    /**
     * [RxDispatcher]发送[RxChange]
     */
    protected fun postRxChange(rxChange: RxChange) {
        rxDispatcher.postRxChange(rxChange)
    }


    /**
     * 创建新的[RxAction]
     *
     * @param tag  tag对应具体是什么样的方法
     * @param data 可变长度参数，键值对型的参数pair-value(String-Object))
     */
    protected fun newRxAction(tag: String, vararg data: Any): RxAction {
        if (data.size % 2 != 0) {
            throw IllegalArgumentException("Data must be a valid list of key,value pairs")
        }
        val actionBuilder = RxAction.Builder(tag)
        var i = 0
        while (i < data.size) {
            val key = data[i++] as String
            val value = data[i++]
            actionBuilder.put(key, value)
        }
        return actionBuilder.build()
    }

    /**
     * 异步执行，成功发送[RxAction]，异常发送[RxError]。
     */
    protected open fun <T> postHttpAction(rxAction: RxAction, httpObservable: Observable<T>) {
        postRxAction(rxAction, httpObservable, canShowLoading = false, canRetry = false)
    }

    /**
     * 异步执行，成功发送[RxAction]，异常发送[RxError]。
     * 开始结束发送[RxLoading]。
     */
    protected open fun <T> postHttpLoadingAction(rxAction: RxAction, httpObservable: Observable<T>) {
        postRxAction(rxAction, httpObservable, canShowLoading = true, canRetry = false)
    }

    /**
     * 异步执行，成功发送[RxAction]，异常发送[RxRetry]。
     */
    protected open fun <T> postHttpRetryAction(rxAction: RxAction, httpObservable: Observable<T>) {
        postRxAction(rxAction, httpObservable, canShowLoading = false, canRetry = true)
    }

    /**
     * 异步执行，成功发送[RxAction]，异常发送[RxRetry]。
     * 开始结束发送[RxLoading]。
     */
    protected open fun <T> postHttpRetryAndLoadingAction(rxAction: RxAction, httpObservable: Observable<T>) {
        postRxAction(rxAction, httpObservable, canShowLoading = true, canRetry = true)
    }

    /**
     * 异步执行重试操作，发送[RxAction]
     */
    fun postRetryAction(rxRetry: RxRetry<*>) {
        val rxAction = newRxAction(rxRetry.tag)
        if (hasRxAction(rxAction)) {
            return
        }
        postHttpRetryAction(rxAction, rxRetry.observable)
    }

    /**
     * 同步发送[RxAction]
     */
    fun postLocalAction(actionId: String, vararg data: Any) {
        val rxAction = newRxAction(actionId, *data)
        postRxAction(rxAction)
        removeRxAction(rxAction)
    }

    /**
     * 同步发送[RxChange]
     */
    fun postLocalChange(tag: String) {
        val rxChange = RxChange.newInstance(tag)
        postRxChange(rxChange)
    }
}
