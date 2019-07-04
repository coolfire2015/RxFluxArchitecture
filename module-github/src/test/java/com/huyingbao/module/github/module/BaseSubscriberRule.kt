package com.huyingbao.module.github.module

import com.huyingbao.core.arch.store.RxStore
import com.huyingbao.core.arch.view.RxSubscriberView
import org.junit.rules.MethodRule
import org.junit.runners.model.FrameworkMethod
import org.junit.runners.model.Statement

/**
 * [com.huyingbao.core.arch.store.RxStore]和[com.huyingbao.core.arch.view.RxSubscriberView]
 * 自动注册订阅和取消订阅
 */
class BaseSubscriberRule : MethodRule {
    override fun apply(base: Statement?, method: FrameworkMethod?, target: Any?): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                if (target is BaseSubscriberTest) {
                    for (subscriber in target.getSubscriberList()) {
                        if (subscriber is RxStore) {
                            target.rxDispatcher.subscribeRxStore(subscriber)
                        } else if (subscriber is RxSubscriberView) {
                            target.rxDispatcher.subscribeRxView(subscriber)
                        }
                    }
                }
                base?.evaluate()
                if (target is BaseSubscriberTest) {
                    for (subscriber in target.getSubscriberList()) {
                        if (subscriber is RxStore) {
                            target.rxDispatcher.unsubscribeRxStore(subscriber)
                        } else if (subscriber is RxSubscriberView) {
                            target.rxDispatcher.unsubscribeRxView(subscriber)
                        }
                    }
                }
            }
        }
    }
}