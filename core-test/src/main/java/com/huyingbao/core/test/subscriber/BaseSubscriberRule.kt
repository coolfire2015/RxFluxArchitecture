package com.huyingbao.core.test.subscriber

import com.huyingbao.core.arch.store.RxStore
import com.huyingbao.core.arch.view.RxSubscriberView
import org.junit.rules.MethodRule
import org.junit.runners.model.FrameworkMethod
import org.junit.runners.model.Statement

/**
 * 自定义的TestRule
 * [com.huyingbao.core.arch.store.RxStore]和[com.huyingbao.core.arch.view.RxSubscriberView]
 * 自动注册订阅和取消订阅。
 *
 * Created by liujunfeng on 2019/7/1.
 */
class BaseSubscriberRule : MethodRule {
    /**
     * @param method 要运行的方法
     * @param target 方法所属的类
     */
    override fun apply(base: Statement?, method: FrameworkMethod?, target: Any?): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                //要运行的测试类，如果是订阅者子类
                if (target is BaseSubscriberTest) {
                    //获取需要注册订阅的对象列表
                    for (subscriber in target.getSubscriberList()) {
                        if (subscriber is RxStore) {
                            //注册订阅RxStore
                            target.rxDispatcher.subscribeRxStore(subscriber)
                        } else if (subscriber is RxSubscriberView) {
                            //注册订阅RxSubscriberView
                            target.rxDispatcher.subscribeRxView(subscriber)
                        }
                    }
                }
                base?.evaluate()
                if (target is BaseSubscriberTest) {
                    //获取需要取消订阅的对象列表
                    for (subscriber in target.getSubscriberList()) {
                        if (subscriber is RxStore) {
                            //取消订阅RxStore
                            target.rxDispatcher.unsubscribeRxStore(subscriber)
                        } else if (subscriber is RxSubscriberView) {
                            //取消订阅RxSubscriberView
                            target.rxDispatcher.unsubscribeRxView(subscriber)
                        }
                    }
                }
            }
        }
    }
}