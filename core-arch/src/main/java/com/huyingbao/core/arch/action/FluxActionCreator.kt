package com.huyingbao.core.arch.action

import com.huyingbao.core.arch.dispatcher.FluxDispatcher
import com.huyingbao.core.arch.model.Action
import com.huyingbao.core.arch.model.Change
import com.huyingbao.core.arch.model.Error
import com.huyingbao.core.arch.model.Loading
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.*
import org.greenrobot.eventbus.BusEvent
import org.greenrobot.eventbus.EventBus
import java.util.logging.Level


/**
 * 实现[com.huyingbao.core.arch.view.FluxView]中调用的交互方法。
 *
 * 创建[Action]，直接发送给[com.huyingbao.core.arch.store.FluxStore]。
 *
 * 创建[Change]、[Loading]、[Error]，直接发送给[com.huyingbao.core.arch.view.FluxView]。
 *
 * 类似MVP模式中的Presenter，只是方法执行完成没有回调方法。
 *
 * Created by liujunfeng on 2020/8/1.
 */
abstract class FluxActionCreator {
    /**
     * 创建新的[Action]
     *
     * @param tag  tag对应具体是什么样的方法
     * @param data 可变长度参数，键值对型的参数pair-value(String-Object))
     */
    protected fun newAction(tag: String, vararg data: Any): Action {
        require(data.size % 2 == 0) { "Data must be a valid list of key,value pairs" }
        val actionBuilder = Action.Builder(tag)
        var i = 0
        while (i < data.size) {
            val key = data[i++] as String
            val value = data[i++]
            actionBuilder.put(key, value)
        }
        return actionBuilder.build()
    }

    /**
     * [FluxActionManager]是否已存在[Action]
     */
    private fun hasAction(action: Action): Boolean {
        return FluxActionManager.contains(action)
    }

    /**
     * [FluxActionManager]添加[Action]和[Job]
     */
    private fun addAction(action: Action, job: Job) {
        FluxActionManager.add(action, job)
    }

    /**
     * [FluxActionManager]移除[Action]，停止对应的[Job]
     */
    protected fun removeAction(action: Action) {
        FluxActionManager.remove(action)
    }

    /**
     * 关联[Action]与[Job]
     *
     * @param canShowLoading true:有进度显示,false:无进度显示
     * @param canRetry       true:操作异常可重试,false:操作异常抛异常
     */
    private fun <T> postAction(
        action: Action,
        flow: Flow<T>,
        canShowLoading: Boolean,
        canRetry: Boolean
    ) {
        if (hasAction(action)) {
            return
        }

        flow
            // 指定IO线程
            .flowOn(Dispatchers.IO)
            // 调用开始
            .onStart {
                if (canShowLoading) {
                    //发送Loading(显示)事件
                    postLoading(action, true)
                }
            }
            // 操作进行中
            .onEach {
                action.setResponse(it)
                FluxDispatcher.postAction(action)
            }
            // 调用结束
            .onCompletion {
                if (canShowLoading) {
                    // 发送Loading(消失)事件
                    postLoading(action, false)
                }
                // 移除事件
                removeAction(action)
            }
            // 捕获异常
            .catch {
                // 操作异常，打印错误日志
                EventBus.getDefault().logger.log(
                    Level.SEVERE,
                    "ActionCreator onError:${it.message}"
                )
                if (canRetry) {
//                        postRetry(action, t, it)
                    } else {
                        postError(action, it)
                    }
                    removeAction(action)
                }
                // 指定主线程执行collect操作
                .launchIn(MainScope())
                // 添加Job到action
                .let {
                    addAction(action, it)
                }
    }

    /**
     * 异步执行，成功发送[Action]，异常发送[Error]。
     */
    protected open fun <T> postHttpAction(action: Action,
                                          flow: Flow<T>) {
        postAction(action, flow, canShowLoading = false, canRetry = false)
    }

    /**
     * 异步执行，成功发送[Action]，异常发送[Error]。
     * 开始结束发送[Loading]。
     */
    protected open fun <T> postHttpLoadingAction(action: Action, flow: Flow<T>) {
        postAction(action, flow, canShowLoading = true, canRetry = false)
    }

    /**
     * [FluxDispatcher]发送[Change]
     */
    protected open fun postChange(change: Change) {
        FluxDispatcher.postChange(change)
    }

    /**
     * [FluxDispatcher]发送[Loading]
     *
     * @param isLoading true:显示，false:消失
     */
    protected open fun postLoading(busEvent: BusEvent, isLoading: Boolean) {
        FluxDispatcher.postLoading(Loading.newInstance(busEvent, isLoading))
    }

    /**
     * [FluxDispatcher]发送[Error]
     */
    protected open fun postError(busEvent: BusEvent, throwable: Throwable) {
        FluxDispatcher.postError(Error.newInstance(busEvent, throwable))
    }
}
