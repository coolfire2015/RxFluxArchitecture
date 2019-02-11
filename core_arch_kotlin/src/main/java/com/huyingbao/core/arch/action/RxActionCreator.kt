package com.huyingbao.core.arch.action

import com.huyingbao.core.arch.dispatcher.RxDispatcher
import com.huyingbao.core.arch.model.RxAction
import com.huyingbao.core.arch.model.RxError
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * 这个类必须被继承,提供一个可以创建RxAction的方法.
 * 按钮被点击触发回调方法，在回调方法中调用ActionCreator提供的有语义的的方法，
 * ActionCreator会根据传入参数创建Action并通过Dispatcher发送给Store，
 * 所有订阅了这个Action的Store会接收到订阅的Action并消化Action，
 * 然后Store会发送UI状态改变的事件给相关的Activity（或Fragment)，
 * Activity在收到状态发生改变的事件之后，开始更新UI（更新UI的过程中会从Store获取所有需要的数据）。
 * Created by liujunfeng on 2019/1/1.
 */
abstract class RxActionCreator(private val mRxDispatcher: RxDispatcher, private val mRxActionManager: RxActionManager) {

    /**
     * 创建新的RxAction
     *
     * @param tag  action tag对应具体是什么样的方法
     * @param data 键值对型的参数pair-value parameters(Key - Object))
     * @return
     */
    protected fun newRxAction(tag: String, vararg data: Any): RxAction {
        if (data != null) {
            if (data.size % 2 != 0)
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
     * 订阅管理器是否已经有了该action
     *
     * @param rxAction
     * @return
     */
    private fun hasRxAction(rxAction: RxAction): Boolean {
        return mRxActionManager.contains(rxAction)
    }

    /**
     * 主要是为了和RxJava整合,用在调用网络接口api获取数据之后,被观察者得到数据,发生订阅关系,将返回的数据
     * 或者error封装成action,postAction或者postError出去
     * 订阅管理,将RxAction和Disposable添加到DisposableManager
     *
     * @param rxAction
     * @param disposable
     */
    private fun addRxAction(rxAction: RxAction, disposable: Disposable) {
        mRxActionManager.add(rxAction, disposable)
    }

    /**
     * 订阅管理器,移除该action
     *
     * @param rxAction
     */
    private fun removeRxAction(rxAction: RxAction) {
        mRxActionManager.remove(rxAction)
    }

    /**
     * 通过调度器dispatcher将action推出去
     *
     * @param action
     */
    protected fun postRxAction(action: RxAction) {
        mRxDispatcher.postRxAction(action)
        removeRxAction(action)
    }

    /**
     * 通过调度器dispatcher将error action推出去
     *
     * @param action
     * @param throwable
     */
    protected fun postRxError(action: RxAction, throwable: Throwable) {
        mRxDispatcher.postRxError(RxError.newRxError(action.tag, throwable))
        removeRxAction(action)
    }

    /**
     * 发送网络action
     *
     * @param rxAction
     * @param httpObservable
     */
    protected fun <T> postHttpAction(rxAction: RxAction, httpObservable: Observable<T>) {
        if (hasRxAction(rxAction)) return
        addRxAction(rxAction, httpObservable// 1:指定IO线程
                .subscribeOn(Schedulers.io())// 1:指定IO线程
                .observeOn(AndroidSchedulers.mainThread())// 2:指定主线程
                .subscribe(// 2:指定主线程
                        { response ->
                            rxAction.setResponse(response)
                            postRxAction(rxAction)
                        },
                        { throwable -> postRxError(rxAction, throwable) }
                ))
    }

    /**
     * 发送本地action
     *
     * @param actionId
     * @param data
     */
    fun postLocalAction(actionId: String, vararg data: Any) {
        postRxAction(newRxAction(actionId, *data))
    }
}
