package com.huyingbao.core.test.subscriber

import com.huyingbao.core.arch.action.ActionManager
import com.huyingbao.core.arch.dispatcher.Dispatcher
import org.junit.Rule
import org.mockito.Spy
import org.mockito.junit.MockitoJUnit

/**
 * 所有Subscriber测试类的父类
 *
 * Created by liujunfeng on 2019/4/3.
 */
abstract class BaseSubscriberTest {
    /**
     * 初始化[org.mockito.Spy]和[org.mockito.Mock]标注的对象
     */
    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    /**
     * [com.huyingbao.core.arch.store.RxStore]和[com.huyingbao.core.arch.view.RxSubscriberView]
     * 自动注册订阅和取消订阅
     */
    @get:Rule
    var actionCreatorRule = BaseSubscriberRule()

    /**
     * 有无参构造函数
     */
    @Spy
    lateinit var dispatcher: Dispatcher

    /**
     * 有无参构造函数
     */
    @Spy
    lateinit var actionManager: ActionManager

    /**
     * 获取所有需要管理订阅的 [com.huyingbao.core.arch.store.RxStore]和[com.huyingbao.core.arch.view.RxSubscriberView]
     * 实现类对象列表
     */
    abstract fun getSubscriberList(): List<Any>
}