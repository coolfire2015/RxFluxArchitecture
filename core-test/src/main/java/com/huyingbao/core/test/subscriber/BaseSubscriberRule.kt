package com.huyingbao.core.test.subscriber

import com.huyingbao.core.arch.store.Store
import com.huyingbao.core.arch.view.SubscriberView
import org.junit.rules.MethodRule
import org.junit.runners.model.FrameworkMethod
import org.junit.runners.model.Statement

/**
 * 自定义的TestRule
 * [com.huyingbao.core.arch.store.Store]和[com.huyingbao.core.arch.view.SubscriberView]
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
                        if (subscriber is Store) {
                            //注册订阅Store
                            target.dispatcher.subscribeStore(subscriber)
                        } else if (subscriber is SubscriberView) {
                            //注册订阅SubscriberView
                            target.dispatcher.subscribeView(subscriber)
                        }
                    }
                }
                base?.evaluate()
                if (target is BaseSubscriberTest) {
                    //获取需要取消订阅的对象列表
                    for (subscriber in target.getSubscriberList()) {
                        if (subscriber is Store) {
                            //取消订阅Store
                            target.dispatcher.unsubscribeStore(subscriber)
                        } else if (subscriber is SubscriberView) {
                            //取消订阅SubscriberView
                            target.dispatcher.unsubscribeView(subscriber)
                        }
                    }
                }
            }
        }
    }
}